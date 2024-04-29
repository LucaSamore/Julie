package com.example.data.gamification

import arrow.core.Either

interface CalculatePointsStrategy {

    fun calculatePoints(
        screenTimeInMillis: Long,
        thresholdInMillis: Long,
        streak: Int
    ): Either<GamificationProblem, Points>
}
