package com.example.domain.user

import arrow.core.Either
import com.example.data.Problem

interface GetLeaderboardUseCase {
    suspend operator fun invoke(): Either<Problem, List<LeaderboardItem>>
}

data class LeaderboardItem(val username: String, val points: Int)
