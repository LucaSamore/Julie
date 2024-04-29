package com.example.data.gamification

import arrow.core.Either
import com.example.data.Problem
import java.time.LocalDateTime

interface DailyChallengeService {
    suspend operator fun invoke(): Either<Problem, LocalDateTime>
}
