package com.example.data.user

import arrow.core.Either
import com.example.data.Repository
import com.example.data.RepositoryProblem
import com.example.data.gamification.Streak

interface UserProfileRepository : Repository<UserProfile, UserId> {
    suspend fun addEndedStreak(streak: Streak): Either<RepositoryProblem, Nothing>

    suspend fun getPastStreaks(userId: UserId): Either<RepositoryProblem, Iterable<Streak>>
}
