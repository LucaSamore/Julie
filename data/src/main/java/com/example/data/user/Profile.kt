package com.example.data.user

import arrow.core.NonEmptyList
import arrow.core.NonEmptySet
import com.example.data.Entity
import com.example.data.Identifier
import com.example.data.gamification.Points
import com.example.data.gamification.Streak
import java.time.LocalDate

typealias UserProblems = NonEmptyList<String>

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

    fun changeInterest(newInterest: NonEmptySet<Interest>): UserProfile

    fun addPoints(pointsToAdd: Points): UserProfile

    fun resetPoints(): UserProfile

    fun incrementCurrentStreak(): UserProfile

    fun endCurrentStreak(): UserProfile
}

@JvmInline value class UserId(private val userId: String) : Identifier

data class CreateAccountDto(
    val firstName: String,
    val lastName: String,
    val birthDate: LocalDate,
    val username: String,
    val emailAddress: String,
    val password: String,
    val interest: NonEmptySet<Interest>
)
