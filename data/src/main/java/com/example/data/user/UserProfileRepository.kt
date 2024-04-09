package com.example.data.user

import arrow.core.Either
import com.example.data.Repository
import com.example.data.gamification.Streak

interface UserProfileRepository : Repository<UserProfile, UserId> {
    suspend fun addEndedStreak(streak: Streak): Either<Nothing, Nothing>

    suspend fun getPastStreaks(userId: UserId): Either<Nothing, Iterable<Streak>>
}
