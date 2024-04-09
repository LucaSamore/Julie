package com.example.data.user.implementation

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.zipOrAccumulate
import com.example.data.gamification.BeginDate
import com.example.data.gamification.EndDate
import com.example.data.gamification.Points
import com.example.data.gamification.Streak
import com.example.data.gamification.StreakValue
import com.example.data.gamification.today
import com.example.data.user.BirthDate
import com.example.data.user.EmailAddress
import com.example.data.user.FirstName
import com.example.data.user.Interest
import com.example.data.user.LastName
import com.example.data.user.Password
import com.example.data.user.UserDetails
import com.example.data.user.UserId
import com.example.data.user.UserProfile
import com.example.data.user.UserValidationError
import com.example.data.user.UserValidationErrors
import com.example.data.user.Username
import java.time.LocalDate
import java.util.UUID

internal class UserProfileImpl
private constructor(
    override val id: UserId,
    override val userDetails: UserDetails,
    override val points: Points,
    override val currentStreak: Streak
) : UserProfile {

    override fun changeFirstName(
        newFirstName: FirstName
    ): Either<UserValidationErrors, UserProfile> = either {
        zipOrAccumulate({}, {}) { _, _ ->
            UserProfileImpl(id, userDetails.copy(firstName = newFirstName), points, currentStreak)
        }
    }

    override fun changeLastName(newLastName: LastName): Either<UserValidationErrors, UserProfile> =
        either {
            zipOrAccumulate({}, {}) { _, _ ->
                UserProfileImpl(id, userDetails.copy(lastName = newLastName), points, currentStreak)
            }
        }

    override fun changeBirthDate(
        newBirthDate: BirthDate
    ): Either<UserValidationErrors, UserProfile> = either {
        zipOrAccumulate({}, {}) { _, _ ->
            UserProfileImpl(id, userDetails.copy(birthDate = newBirthDate), points, currentStreak)
        }
    }

    override fun changeUsername(newUsername: Username): Either<UserValidationErrors, UserProfile> =
        either {
            zipOrAccumulate({}, {}) { _, _ ->
                UserProfileImpl(id, userDetails.copy(username = newUsername), points, currentStreak)
            }
        }

    override fun changeEmailAddress(
        newEmailAddress: EmailAddress
    ): Either<UserValidationErrors, UserProfile> = either {
        zipOrAccumulate({}, {}) { _, _ ->
            UserProfileImpl(
                id,
                userDetails.copy(emailAddress = newEmailAddress),
                points,
                currentStreak
            )
        }
    }

    override fun changePassword(newPassword: Password): Either<UserValidationErrors, UserProfile> =
        either {
            zipOrAccumulate({}, {}) { _, _ ->
                UserProfileImpl(id, userDetails.copy(password = newPassword), points, currentStreak)
            }
        }

    override fun changeInterest(
        newInterest: Iterable<Interest>
    ): Either<UserValidationErrors, UserProfile> = either {
        zipOrAccumulate({}, {}) { _, _ ->
            UserProfileImpl(id, userDetails.copy(interest = newInterest), points, currentStreak)
        }
    }

    override fun addPoints(pointsToAdd: Points): Either<UserValidationError, UserProfile> = either {
        ensure(points > 0) { UserValidationError.Error }
        UserProfileImpl(id, userDetails, points + pointsToAdd, currentStreak)
    }

    override fun resetPoints(): UserProfile =
        UserProfileImpl(id, userDetails, Points(0), currentStreak)

    override fun incrementCurrentStreak(): UserProfile =
        UserProfileImpl(id, userDetails, points, currentStreak incrementBy 1)

    override fun endCurrentStreak(): UserProfile =
        UserProfileImpl(id, userDetails, points, currentStreak ends today())

    companion object {
        operator fun invoke(
            id: UserId,
            userDetails: UserDetails,
            points: Points,
            currentStreak: Streak
        ): Either<UserValidationErrors, UserProfile> = either {
            zipOrAccumulate({}, {}, {}, {}) { _, _, _, _ ->
                UserProfileImpl(id, userDetails, points, currentStreak)
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
    birthDate: LocalDate,
    username: String,
    emailAddress: String,
    password: String,
    interest: Iterable<Interest>
): Either<UserValidationErrors, UserProfile> {
    val userId = UserId(UUID.randomUUID().toString())

    return UserProfileImpl(
        userId,
        UserDetails(
            FirstName(firstName),
            LastName(lastName),
            BirthDate(birthDate),
            Username(username),
            EmailAddress(emailAddress),
            Password(password),
            interest
        ),
        Points(0),
        Streak(userId, StreakValue(0), BeginDate(LocalDate.now()), EndDate(null))
    )
}
