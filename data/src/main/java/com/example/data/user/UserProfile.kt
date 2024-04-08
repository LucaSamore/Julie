package com.example.data.user

import arrow.core.Either
import arrow.core.NonEmptyList
import com.example.data.Entity
import com.example.data.Identifier
import com.example.data.gamification.Points
import com.example.data.gamification.Streak
import java.time.LocalDate

typealias UserValidationErrors = NonEmptyList<UserValidationError>

interface UserProfile : Entity<UserId> {
    val userDetails: UserDetails

    val points: Points

    val currentStreak: Streak

    val pastStreaks: PastStreaks

    fun changeFirstName(newFirstName: FirstName): Either<UserValidationErrors, UserProfile>

    fun changeLastName(newLastName: LastName): Either<UserValidationErrors, UserProfile>

    fun changeBirthDate(newBirthDate: BirthDate): Either<UserValidationErrors, UserProfile>

    fun changeUsername(newUsername: Username): Either<UserValidationErrors, UserProfile>

    fun changeEmailAddress(newEmailAddress: EmailAddress): Either<UserValidationErrors, UserProfile>

    fun changePassword(newPassword: Password): Either<UserValidationErrors, UserProfile>

    fun changeInterest(newInterest: Iterable<Interest>): Either<UserValidationErrors, UserProfile>

    fun addPoints(pointsToAdd: Points): Either<UserValidationError, UserProfile>

    fun resetPoints(): UserProfile

    fun incrementCurrentStreak(): UserProfile

    fun endCurrentStreak(): Either<UserValidationErrors, UserProfile>
}

sealed interface UserValidationError {
    data object Error : UserValidationError
}

@JvmInline value class UserId(private val userId: String) : Identifier

data class UserDetails(
    val firstName: FirstName,
    val lastName: LastName,
    val birthDate: BirthDate,
    val username: Username,
    val emailAddress: EmailAddress,
    val password: Password,
    val interest: Iterable<Interest>
)

@JvmInline value class FirstName(private val firstName: String)

@JvmInline value class LastName(private val lastName: String)

@JvmInline value class BirthDate(private val birthDate: LocalDate)

@JvmInline value class Username(private val username: String)

@JvmInline value class EmailAddress(private val emailAddress: String)

@JvmInline value class Password(private val password: String)

data class Interest(val name: Name, val category: Category)

@JvmInline value class Name(private val name: String)

@JvmInline value class Category(private val category: String)

@JvmInline value class PastStreaks(private val pastStreaks: Iterable<Streak>)
