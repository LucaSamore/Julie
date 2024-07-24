package com.example.data.user.implementation

import arrow.core.Either
import arrow.core.flatMap
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.data.EntityDeleted
import com.example.data.Problem
import com.example.data.RepositoryProblem
import com.example.data.gamification.Streak
import com.example.data.gamification.implementation.FirestoreStreakDto
import com.example.data.user.EmailAddress
import com.example.data.user.UserId
import com.example.data.user.UserProfile
import com.example.data.user.UserProfileRepository
import com.example.data.user.Username
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

internal class UserProfileFirestoreRepository : UserProfileRepository {

    private val db: FirebaseFirestore = Firebase.firestore

    override suspend fun create(entity: UserProfile): Either<RepositoryProblem, UserProfile> {
        return Either.catch {
                db.collection(FirestoreUserDto.COLLECTION)
                    .document(entity.id.value)
                    .set(
                        FirestoreUserDto.fromEntity(entity)
                            .copy(password = hashPassword(entity.userDetails.password.value))
                    )
                    .await()
            }
            .mapLeft { RepositoryProblem.fromThrowable(it) }
            .map { entity }
    }

    override suspend fun findMany(): Either<RepositoryProblem, Iterable<UserProfile>> {
        return Either.catch {
                db.collection(FirestoreUserDto.COLLECTION)
                    .get()
                    .await()
                    .toObjects(FirestoreUserDto::class.java)
            }
            .mapLeft { RepositoryProblem.fromThrowable(it) }
            .map { it.mapNotNull { dto -> FirestoreUserDto.toEntity(dto).getOrNull() } }
    }

    override suspend fun findOne(id: UserId): Either<RepositoryProblem, UserProfile> {
        return Either.catch {
                val dto =
                    db.collection(FirestoreUserDto.COLLECTION)
                        .document(id.value)
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
                    .document(entity.id.value)
                    .update(FirestoreUserDto.entityToDocumentFields(entity))
                    .await()
            }
            .mapLeft { RepositoryProblem.fromThrowable(it) }
            .map { entity }
    }

    override suspend fun delete(id: UserId): Either<RepositoryProblem, EntityDeleted> {
        return Either.catch {
                db.collection(FirestoreUserDto.COLLECTION).document(id.value).delete().await()
            }
            .mapLeft { RepositoryProblem.fromThrowable(it) }
            .map { EntityDeleted }
    }

    override suspend fun getUserIdByEmailAddress(
        emailAddress: EmailAddress
    ): Either<Problem, UserId> {
        return Either.catch {
                db.collection(FirestoreUserDto.COLLECTION)
                    .whereEqualTo("emailAddress", emailAddress.value)
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
                    .whereEqualTo("emailAddress", emailAddress.value)
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
                    .whereEqualTo("username", username.value)
                    .get()
                    .await()
            }
            .mapLeft { RepositoryProblem.fromThrowable(it) }
            .map { documents -> !documents.isEmpty }
    }

    override suspend fun addEndedStreak(streak: Streak): Either<RepositoryProblem, Streak> {
        return Either.catch {
                db.collection(FirestoreStreakDto.COLLECTION)
                    .add(FirestoreStreakDto.fromEntity(streak))
                    .await()
            }
            .mapLeft { RepositoryProblem.fromThrowable(it) }
            .map { streak }
    }

    override suspend fun getLeaderboard(): Either<RepositoryProblem, Iterable<UserProfile>> {
        return Either.catch {
                db.collection(FirestoreUserDto.COLLECTION)
                    .whereGreaterThan("points", 0)
                    .get()
                    .await()
                    .toObjects(FirestoreUserDto::class.java)
            }
            .mapLeft { RepositoryProblem.fromThrowable(it) }
            .map { it.mapNotNull { dto -> FirestoreUserDto.toEntity(dto).getOrNull() } }
    }

    companion object {
        private const val BCRYPT_COST = 12

        private fun hashPassword(plainPassword: String): String =
            BCrypt.withDefaults().hashToString(BCRYPT_COST, plainPassword.toCharArray())
    }
}
