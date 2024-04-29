package com.example.data.user.implementation

import arrow.core.Either
import arrow.core.flatMap
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.data.Problem
import com.example.data.RepositoryProblem
import com.example.data.gamification.Streak
import com.example.data.user.EmailAddress
import com.example.data.user.UserId
import com.example.data.user.UserProfile
import com.example.data.user.UserProfileRepository
import com.example.data.user.Username
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

internal class UserProfileRepositoryImpl : UserProfileRepository {

    private val db: FirebaseFirestore = Firebase.firestore

    override suspend fun create(entity: UserProfile): Either<RepositoryProblem, UserProfile> {
        return Either.catch {
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
    }

    override suspend fun findMany(): Either<RepositoryProblem, Iterable<UserProfile>> {
        TODO("Not yet implemented")
    }

    override suspend fun findOne(id: UserId): Either<RepositoryProblem, UserProfile> {
        return Either.catch {
                val dto =
                    db.collection(FirestoreUserDto.COLLECTION)
                        .document(id.userId)
                        .get()
                        .await()
                        .toObject(FirestoreUserDto::class.java)!!
                FirestoreUserDto.toEntity(dto)
            }
            .mapLeft { RepositoryProblem.fromThrowable(it) }
            .flatMap {
                it.mapLeft { problems ->
                    val message =
                        problems
                            .map { problem -> problem.message }
                            .joinToString(prefix = "Problems:\n", separator = "\n")
                    RepositoryProblem(message)
                }
            }
    }

    override suspend fun update(entity: UserProfile): Either<RepositoryProblem, UserProfile> {
        return Either.catch {
                db.collection(FirestoreUserDto.COLLECTION)
                    .document(entity.id.userId)
                    .update(
                        mapOf(
                            "points" to entity.points.points,
                            "threshold.valueInMillis" to
                                entity.threshold.valueInMillis.valueInMillis,
                            "threshold.nextReset" to
                                entity.threshold.nextReset.nextReset.toString(),
                            "currentStreak.value" to entity.currentStreak.value.value,
                            "currentStreak.started" to entity.currentStreak.begin.value.toString(),
                            "currentStreak.ended" to
                                if (entity.currentStreak.end.value == null) null
                                else entity.currentStreak.end.value.toString()
                        )
                    )
                    .await()
            }
            .mapLeft { RepositoryProblem.fromThrowable(it) }
            .map { entity }
    }

    override suspend fun delete(id: UserId): Either<RepositoryProblem, UserProfile> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserIdByEmailAddress(
        emailAddress: EmailAddress
    ): Either<Problem, UserId> {
        return Either.catch {
                db.collection(FirestoreUserDto.COLLECTION)
                    .whereEqualTo("emailAddress", emailAddress.emailAddress)
                    .get()
                    .await()
                    .toObjects(FirestoreUserDto::class.java)
            }
            .mapLeft { RepositoryProblem.fromThrowable(it) }
            .flatMap { documents -> UserId(documents.first().id ?: "") }
    }

    override suspend fun isEmailAddressAlreadyInUse(
        emailAddress: EmailAddress
    ): Either<RepositoryProblem, Boolean> {
        return Either.catch {
                db.collection(FirestoreUserDto.COLLECTION)
                    .whereEqualTo("emailAddress", emailAddress.emailAddress)
                    .get()
                    .await()
            }
            .mapLeft { RepositoryProblem.fromThrowable(it) }
            .map { documents -> !documents.isEmpty }
    }

    override suspend fun isUsernameAlreadyInUse(
        username: Username
    ): Either<RepositoryProblem, Boolean> {
        return Either.catch {
                db.collection(FirestoreUserDto.COLLECTION)
                    .whereEqualTo("username", username.username)
                    .get()
                    .await()
            }
            .mapLeft { RepositoryProblem.fromThrowable(it) }
            .map { documents -> !documents.isEmpty }
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
