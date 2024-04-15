package com.example.data.user

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.raise.either
import arrow.core.raise.ensure
import com.example.data.Entity
import com.example.data.Identifier
import com.example.data.Problem
import com.example.data.gamification.Points
import com.example.data.gamification.Streak

interface UserProfile : Entity<UserId> {
    val userDetails: UserDetails

    val points: Points

    val currentStreak: Streak

    fun changeFirstName(newFirstName: FirstName): UserProfile

    fun changeLastName(newLastName: LastName): UserProfile

    fun changeBirthDate(newBirthDate: BirthDate): UserProfile

    fun changeUsername(newUsername: Username): UserProfile

    fun changeEmailAddress(newEmailAddress: EmailAddress): UserProfile

    fun changePassword(newPassword: Password): UserProfile

    fun changeInterest(newInterest: List<Interest>): UserProfile

    fun addPoints(pointsToAdd: Points): UserProfile

    fun resetPoints(): UserProfile

    fun incrementCurrentStreak(): UserProfile

    fun endCurrentStreak(): UserProfile
}

typealias UserProblems = NonEmptyList<UserProblem>

@JvmInline
value class UserId private constructor(val userId: String) : Identifier {
    companion object {
        operator fun invoke(userId: String): Either<UserProblem, UserId> = either {
            ensure(userId.isNotEmpty()) { UserIdProblem("User id cannot be empty") }

            UserId(userId)
        }
    }
}

sealed interface UserProblem : Problem

@JvmInline value class UserIdProblem(override val message: String) : UserProblem

@JvmInline value class FirstNameProblem(override val message: String) : UserProblem

@JvmInline value class LastNameProblem(override val message: String) : UserProblem

@JvmInline value class BirthDateProblem(override val message: String) : UserProblem

@JvmInline value class UsernameProblem(override val message: String) : UserProblem

@JvmInline value class EmailAddressProblem(override val message: String) : UserProblem

@JvmInline value class PasswordProblem(override val message: String) : UserProblem
