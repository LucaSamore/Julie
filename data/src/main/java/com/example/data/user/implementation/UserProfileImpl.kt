package com.example.data.user.implementation

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.raise.either
import arrow.core.raise.zipOrAccumulate
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
import com.example.data.user.UserDetails
import com.example.data.user.UserId
import com.example.data.user.UserProfile
import com.example.data.user.UserValidationError
import com.example.data.user.Username
import java.time.LocalDate
import java.util.UUID

internal class UserProfileImpl
private constructor(
    override val id: UserId,
    override val userDetails: UserDetails,
    override val points: Points,
    override val currentStreak: Streak,
    override val pastStreaks: PastStreaks
) : UserProfile {
    companion object {
        operator fun invoke(
            id: UserId,
            userDetails: UserDetails,
            points: Points,
            currentStreak: Streak,
            pastStreaks: PastStreaks
        ): Either<NonEmptyList<UserValidationError>, UserProfile> = either {
            zipOrAccumulate({}, {}, {}, {}) { _, _, _, _ ->
                UserProfileImpl(id, userDetails, points, currentStreak, pastStreaks)
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserProfileImpl

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

fun UserProfile.createNewAccount(
    firstName: String,
    lastName: String,
    username: String,
    emailAddress: String,
    password: String,
    interest: Iterable<Interest>
): Either<NonEmptyList<UserValidationError>, UserProfile> =
    UserProfileImpl(
        UserId(UUID.randomUUID().toString()),
        UserDetails(
            FirstName(firstName),
            LastName(lastName),
            Username(username),
            EmailAddress(emailAddress),
            Password(password),
            interest
        ),
        Points(0),
        Streak(StreakValue(0), BeginDate(LocalDate.now()), EndDate(null)),
        PastStreaks(emptyList())
    )
