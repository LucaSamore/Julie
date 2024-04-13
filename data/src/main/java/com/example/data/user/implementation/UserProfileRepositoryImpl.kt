package com.example.data.user.implementation

import arrow.core.Either
import com.example.data.gamification.Streak
import com.example.data.user.UserId
import com.example.data.user.UserProfile
import com.example.data.user.UserProfileRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher

internal class UserProfileRepositoryImpl
@Inject
constructor(private val ioDispatcher: CoroutineDispatcher) : UserProfileRepository {

    override suspend fun create(entity: UserProfile): Either<Nothing, Nothing> {
        TODO("Not yet implemented")
    }

    override suspend fun findMany(): Either<Nothing, Sequence<UserProfile>> {
        TODO("Not yet implemented")
    }

    override suspend fun findOne(id: UserId): Either<Nothing, UserProfile> {
        TODO("Not yet implemented")
    }

    override suspend fun update(entity: UserProfile): Either<Nothing, Nothing> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: UserId): Either<Nothing, Nothing> {
        TODO("Not yet implemented")
    }

    override suspend fun addEndedStreak(streak: Streak): Either<Nothing, Nothing> {
        TODO("Not yet implemented")
    }

    override suspend fun getPastStreaks(userId: UserId): Either<Nothing, Iterable<Streak>> {
        TODO("Not yet implemented")
    }
}
