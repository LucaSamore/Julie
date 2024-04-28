package com.example.data.gamification.implementation

import arrow.core.Either
import arrow.core.raise.either
import com.example.data.Problem
import com.example.data.gamification.DailyChallengeService
import com.example.data.gamification.Points
import com.example.data.statistics.StatisticsDataSource
import com.example.data.user.UserProfileRepository
import com.example.data.user.implementation.UserDatastore
import com.example.data.util.today
import javax.inject.Inject
import kotlin.math.abs

internal class DailyChallengeServiceImpl
@Inject
constructor(
    private val userProfileRepository: UserProfileRepository,
    private val userDatastore: UserDatastore,
    private val statisticsDataSource: StatisticsDataSource,
) : DailyChallengeService {

    override suspend fun invoke(): Either<Problem, Unit> = either {
        val userId = userDatastore.getUserId().bind()
        val user = userProfileRepository.findOne(userId).bind()
        val threshold = user.threshold
        val screenTime = statisticsDataSource.getCurrentScreenTime()

        val updatedUser =
            if (screenTime <= threshold.valueInMillis.valueInMillis) {
                val pointsGaines =
                    calculatePoints(screenTime, threshold.valueInMillis.valueInMillis).bind()
                user.incrementCurrentStreak().addPoints(pointsGaines).let {
                    if (threshold.nextReset.nextReset == today()) {
                        it.resetThreshold()
                    } else {
                        it.decreaseThreshold()
                    }
                }
            } else {
                user.endCurrentStreak().resetPoints().let {
                    if (threshold.nextReset.nextReset == today()) {
                        it.resetThreshold()
                    } else {
                        it.increaseThreshold()
                    }
                }
            }
        userProfileRepository.update(updatedUser)
    }

    private fun calculatePoints(screenTime: Long, threshold: Long) =
        Points(abs(screenTime - threshold).toInt())
}
