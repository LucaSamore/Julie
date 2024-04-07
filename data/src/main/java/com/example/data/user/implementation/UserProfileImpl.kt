package com.example.data.user.implementation

import arrow.core.NonEmptySet
import com.example.data.gamification.BeginDate
import com.example.data.gamification.EndDate
import com.example.data.gamification.Points
import com.example.data.gamification.Streak
import com.example.data.gamification.StreakValue
import com.example.data.user.EmailAddress
import com.example.data.user.FirstName
import com.example.data.user.Interest
import com.example.data.user.LastName
import com.example.data.user.Password
import com.example.data.user.PastStreaks
import com.example.data.user.UserId
import com.example.data.user.UserProfile
import com.example.data.user.Username
import java.time.LocalDate
import java.util.UUID

internal data class UserProfileImpl(
    override val userId: UserId,
    override val firstName: FirstName,
    override val lastName: LastName,
    override val userName: Username,
    override val emailAddress: EmailAddress,
    override val password: Password,
    override val interest: NonEmptySet<Interest>,
    override val points: Points,
    override val currentStreak: Streak,
    override val pastStreaks: PastStreaks
) : UserProfile

fun UserProfile.createNewAccount(
    firstName: String,
    lastName: String,
    username: String,
    emailAddress: String,
    password: String,
    vararg interest: Interest
): UserProfile =
    UserProfileImpl(
        UserId(UUID.randomUUID().toString()),
        FirstName(firstName),
        LastName(lastName),
        Username(username),
        EmailAddress(emailAddress),
        Password(password),
        NonEmptySet(interest.first(), interest.slice(1 until interest.size).toSet()),
        Points(0),
        Streak(StreakValue(0), BeginDate(LocalDate.now()), EndDate(null)),
        PastStreaks(emptySequence())
    )
