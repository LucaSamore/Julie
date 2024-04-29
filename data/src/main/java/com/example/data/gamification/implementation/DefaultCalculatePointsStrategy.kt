package com.example.data.gamification.implementation

import arrow.core.Either
import com.example.data.gamification.CalculatePointsStrategy
import com.example.data.gamification.GamificationProblem
import com.example.data.gamification.Points
import com.example.data.util.inMinutes
import kotlin.math.abs

internal class DefaultCalculatePointsStrategy : CalculatePointsStrategy {

    override fun calculatePoints(
        screenTimeInMillis: Long,
        thresholdInMillis: Long,
        streak: Int,
    ): Either<GamificationProblem, Points> {
        val pointsGained =
            abs(screenTimeInMillis.inMinutes() - thresholdInMillis.inMinutes()).toInt() + streak
        return Points(pointsGained)
    }
}
