package com.example.data.gamification

import arrow.core.Either
import com.example.data.Problem

interface DailyChallengeService {
    suspend operator fun invoke(): Either<Problem, Unit>
}
