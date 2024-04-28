package com.example.domain.user

import arrow.core.Either
import com.example.data.Problem

interface GetUserProfileGamificationDataUseCase {
    suspend operator fun invoke(): Either<Problem, UserGamificationDataDto>
}

data class UserGamificationDataDto(
    val threshold: Long,
    val points: Int,
    val currentStreakValue: Int
)
