package com.example.data.user.implementation

import arrow.core.Either
import arrow.core.NonEmptySet
import arrow.core.raise.either
import arrow.core.raise.zipOrAccumulate
import com.example.data.gamification.BeginDate
import com.example.data.gamification.EndDate
import com.example.data.gamification.Points
import com.example.data.gamification.Streak
import com.example.data.gamification.StreakValue
import com.example.data.gamification.today
import com.example.data.user.BirthDate
import com.example.data.user.CreateAccountDto
import com.example.data.user.EmailAddress
import com.example.data.user.FirstName
import com.example.data.user.Interest
import com.example.data.user.LastName
import com.example.data.user.Password
import com.example.data.user.UserDetails
import com.example.data.user.UserId
import com.example.data.user.UserProblems
import com.example.data.user.UserProfile
import com.example.data.user.Username
import java.time.LocalDate
import java.util.UUID

internal class UserProfileImpl(
    override val id: UserId,
    override val userDetails: UserDetails,
    override val points: Points,
    override val currentStreak: Streak
) : UserProfile {

    override fun changeFirstName(newFirstName: FirstName): UserProfile =
        UserProfileImpl(id, userDetails.copy(firstName = newFirstName), points, currentStreak)

    override fun changeLastName(newLastName: LastName): UserProfile =
        UserProfileImpl(id, userDetails.copy(lastName = newLastName), points, currentStreak)

    override fun changeBirthDate(newBirthDate: BirthDate): UserProfile =
        UserProfileImpl(id, userDetails.copy(birthDate = newBirthDate), points, currentStreak)

    override fun changeUsername(newUsername: Username): UserProfile =
        UserProfileImpl(id, userDetails.copy(username = newUsername), points, currentStreak)

    override fun changeEmailAddress(newEmailAddress: EmailAddress): UserProfile =
        UserProfileImpl(id, userDetails.copy(emailAddress = newEmailAddress), points, currentStreak)

    override fun changePassword(newPassword: Password): UserProfile =
        UserProfileImpl(id, userDetails.copy(password = newPassword), points, currentStreak)

    override fun changeInterest(newInterest: NonEmptySet<Interest>): UserProfile =
        UserProfileImpl(id, userDetails.copy(interest = newInterest), points, currentStreak)

    override fun addPoints(pointsToAdd: Points): UserProfile =
        UserProfileImpl(id, userDetails, points + pointsToAdd, currentStreak)

    override fun resetPoints(): UserProfile =
        UserProfileImpl(id, userDetails, Points(0), currentStreak)

    override fun incrementCurrentStreak(): UserProfile =
        UserProfileImpl(id, userDetails, points, currentStreak incrementedBy 1)

    override fun endCurrentStreak(): UserProfile =
        UserProfileImpl(id, userDetails, points, currentStreak ends today())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as UserProfileImpl
        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}

fun UserProfile.createNewAccount(
    createAccountDto: CreateAccountDto
): Either<UserProblems, UserProfile> = either {
    zipOrAccumulate(
        { FirstName(createAccountDto.firstName).bind() },
        { LastName(createAccountDto.lastName).bind() },
        { BirthDate(createAccountDto.birthDate).bind() },
        { Username(createAccountDto.username).bind() },
        { EmailAddress(createAccountDto.emailAddress).bind() },
        { Password(createAccountDto.password).bind() },
    ) { firstName, lastName, birthDate, username, emailAddress, password ->
        val userId = UserId(UUID.randomUUID().toString())
        UserProfileImpl(
            userId,
            UserDetails(
                firstName,
                lastName,
                birthDate,
                username,
                emailAddress,
                password,
                createAccountDto.interest
            ),
            Points(0),
            Streak(userId, StreakValue(0), BeginDate(LocalDate.now()), EndDate(null))
        )
    }
}
