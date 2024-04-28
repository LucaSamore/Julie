package com.example.data.user

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import com.example.data.Entity
import com.example.data.Identifier
import com.example.data.Problem
import com.example.data.gamification.Points
import com.example.data.gamification.Streak
import com.example.data.gamification.Threshold

interface UserProfile : Entity<UserId> {
    val userDetails: UserDetails

    val points: Points

    val threshold: Threshold

    val currentStreak: Streak

    fun addPoints(pointsToAdd: Points): UserProfile

    fun resetPoints(): UserProfile

    fun resetThreshold(): UserProfile

    fun increaseThreshold(): UserProfile

    fun decreaseThreshold(): UserProfile

    fun incrementCurrentStreak(): UserProfile

    fun endCurrentStreak(): UserProfile
}

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
