package com.example.data.gamification.implementation

import arrow.core.Either
import arrow.core.raise.either
import com.example.data.Problem
import com.example.data.gamification.CalculatePointsStrategy
import com.example.data.gamification.DailyChallengeService
import com.example.data.gamification.Threshold
import com.example.data.statistics.StatisticsDataSource
import com.example.data.user.UserProfile
import com.example.data.user.UserProfileRepository
import com.example.data.user.implementation.UserDatastore
import com.example.data.util.today
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

internal class DailyChallengeServiceImpl
@Inject
constructor(
    private val userProfileRepository: UserProfileRepository,
    private val userDatastore: UserDatastore,
    private val statisticsDataSource: StatisticsDataSource,
    private val pointsStrategy: CalculatePointsStrategy
) : DailyChallengeService {

    override suspend fun invoke(): Either<Problem, LocalDateTime> = either {
        val userId = userDatastore.getUserId().bind()
        val user = userProfileRepository.findOne(userId).bind()
        val threshold = user.threshold
        val endTime = userDatastore.getDateTimeOfRecording().bind()
        val screenTimeInMillis =
            statisticsDataSource.getCurrentScreenTime(
                endTime.toInstant(ZoneOffset.UTC).toEpochMilli()
            )
        val updatedUser =
            when {
                (screenTimeInMillis <= threshold.valueInMillis.valueInMillis) -> {
                    addPointsAndIncreaseStreak(user, screenTimeInMillis, threshold)
                        .bind()
                        .decreaseOrResetThreshold()
                }
                else -> {
                    resetPointsAndEndStreak(user).bind().increaseOrResetThreshold()
                }
            }
        userProfileRepository.update(updatedUser)
        endTime.plusDays(1)
    }

    private fun addPointsAndIncreaseStreak(
        user: UserProfile,
        screenTime: Long,
        threshold: Threshold
    ): Either<Problem, UserProfile> = either {
        val pointsGaines =
            pointsStrategy
                .calculatePoints(
                    screenTime,
                    threshold.valueInMillis.valueInMillis,
                    user.currentStreak.value.value
                )
                .bind()
        user.incrementCurrentStreak().addPoints(pointsGaines)
    }

    private fun <T : UserProfile> T.decreaseOrResetThreshold(): UserProfile =
        if (threshold.nextReset.nextReset == today()) {
            resetThreshold()
        } else {
            decreaseThreshold()
        }

    private suspend fun resetPointsAndEndStreak(
        user: UserProfile,
    ): Either<Problem, UserProfile> = either {
        user
            .also {
                userProfileRepository.addEndedStreak(user.endCurrentStreak().currentStreak).bind()
            }
            .resetStreak()
            .resetPoints()
    }

    private fun <T : UserProfile> T.increaseOrResetThreshold(): UserProfile =
        if (threshold.nextReset.nextReset == today()) {
            resetThreshold()
        } else {
            increaseThreshold()
        }
}
