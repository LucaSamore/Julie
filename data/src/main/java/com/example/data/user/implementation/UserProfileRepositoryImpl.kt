package com.example.data.user.implementation

import arrow.core.Either
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.data.RepositoryProblem
import com.example.data.gamification.Streak
import com.example.data.user.UserId
import com.example.data.user.UserProfile
import com.example.data.user.UserProfileRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

internal class UserProfileRepositoryImpl : UserProfileRepository {

    private val db: FirebaseFirestore = Firebase.firestore

    override suspend fun create(entity: UserProfile): Either<RepositoryProblem, UserProfile> =
        Either.catch {
                db.collection(FirestoreUserDto.COLLECTION)
                    .document(entity.id.userId)
                    .set(
                        FirestoreUserDto.fromEntity(entity)
                            .copy(password = hashPassword(entity.userDetails.password.password))
                    )
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

    companion object {
        private const val BCRYPT_COST = 12

        fun hashPassword(plainPassword: String): String =
            BCrypt.withDefaults().hashToString(BCRYPT_COST, plainPassword.toCharArray())
    }
}
