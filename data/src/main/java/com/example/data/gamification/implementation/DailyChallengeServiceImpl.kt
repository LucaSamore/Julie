package com.example.data.gamification.implementation

import arrow.core.Either
import arrow.core.raise.either
import com.example.data.Problem
import com.example.data.gamification.CalculatePointsStrategy
import com.example.data.gamification.DailyChallengeService
import com.example.data.statistics.StatisticsDataSource
import com.example.data.user.UserProfileRepository
import com.example.data.user.implementation.UserDatastore
import com.example.data.util.today
import javax.inject.Inject

internal class DailyChallengeServiceImpl
@Inject
constructor(
    private val userProfileRepository: UserProfileRepository,
    private val userDatastore: UserDatastore,
    private val statisticsDataSource: StatisticsDataSource,
    private val pointsStrategy: CalculatePointsStrategy
) : DailyChallengeService {

    override suspend fun invoke(): Either<Problem, Unit> = either {
        val userId = userDatastore.getUserId().bind()
        val user = userProfileRepository.findOne(userId).bind()
        val threshold = user.threshold
        val screenTime = statisticsDataSource.getCurrentScreenTime()
        val updatedUser =
            if (screenTime <= threshold.valueInMillis.valueInMillis) {
                val pointsGaines =
                    pointsStrategy
                        .calculatePoints(
                            screenTime,
                            threshold.valueInMillis.valueInMillis,
                            user.currentStreak.value.value
                        )
                        .bind()
                user.incrementCurrentStreak().addPoints(pointsGaines).let {
                    if (threshold.nextReset.nextReset == today()) {
                        it.resetThreshold()
                    } else {
                        it.decreaseThreshold()
                    }
                }
            } else {
                user
                    .also {
                        userProfileRepository
                            .addEndedStreak(user.endCurrentStreak().currentStreak)
                            .bind()
                    }
                    .resetStreak()
                    .resetPoints()
                    .let {
                        if (threshold.nextReset.nextReset == today()) {
                            it.resetThreshold()
                        } else {
                            it.increaseThreshold()
                        }
                    }
            }
        userProfileRepository.update(updatedUser)
    }
}
