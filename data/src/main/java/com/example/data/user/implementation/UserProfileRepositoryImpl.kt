package com.example.data.user.implementation

import arrow.core.Either
import com.example.data.RepositoryProblem
import com.example.data.gamification.Streak
import com.example.data.user.UserId
import com.example.data.user.UserProfile
import com.example.data.user.UserProfileRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await

internal class UserProfileRepositoryImpl
@Inject
constructor(private val ioDispatcher: CoroutineDispatcher) : UserProfileRepository {

    private val db = Firebase.firestore

    override suspend fun create(entity: UserProfile): Either<RepositoryProblem, UserProfile> =
        Either.catch {
                db.collection(FirestoreUserDto.COLLECTION)
                    .document(entity.id.userId)
                    .set(FirestoreUserDto.fromEntity(entity))
                    .await()
            }
            .mapLeft { RepositoryProblem.fromThrowable(it) }
            .map { entity }

    override suspend fun findMany(): Either<RepositoryProblem, Iterable<UserProfile>> {
        TODO("Not yet implemented")
    }

    override suspend fun findOne(id: UserId): Either<RepositoryProblem, UserProfile> {
        TODO("Not yet implemented")
    }

    override suspend fun update(entity: UserProfile): Either<RepositoryProblem, UserProfile> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: UserId): Either<RepositoryProblem, UserProfile> {
        TODO("Not yet implemented")
    }

    override suspend fun addEndedStreak(streak: Streak): Either<RepositoryProblem, UserProfile> {
        TODO("Not yet implemented")
    }

    override suspend fun getPastStreaks(
        userId: UserId
    ): Either<RepositoryProblem, Iterable<Streak>> {
        TODO("Not yet implemented")
    }
}
