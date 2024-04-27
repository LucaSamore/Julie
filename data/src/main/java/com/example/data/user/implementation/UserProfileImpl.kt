package com.example.data.user.implementation

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.zipOrAccumulate
import com.example.data.gamification.BeginDate
import com.example.data.gamification.EndDate
import com.example.data.gamification.NextReset
import com.example.data.gamification.Points
import com.example.data.gamification.Streak
import com.example.data.gamification.StreakValue
import com.example.data.gamification.Threshold
import com.example.data.gamification.ThresholdValue
import com.example.data.user.BirthDate
import com.example.data.user.CreateAccountDto
import com.example.data.user.EmailAddress
import com.example.data.user.FirstName
import com.example.data.user.LastName
import com.example.data.user.Password
import com.example.data.user.UserDetails
import com.example.data.user.UserId
import com.example.data.user.UserProblems
import com.example.data.user.UserProfile
import com.example.data.user.Username
import java.time.LocalDate
import java.util.UUID
import kotlin.time.Duration.Companion.hours

internal data class UserProfileImpl(
    override val id: UserId,
    override val userDetails: UserDetails,
    override val points: Points,
    override val threshold: Threshold,
    override val currentStreak: Streak
) : UserProfile

fun createNewAccount(createAccountDto: CreateAccountDto): Either<UserProblems, UserProfile> =
    either {
        zipOrAccumulate(
            { UserId(UUID.randomUUID().toString()).bind() },
            { FirstName(createAccountDto.firstName).bind() },
            { LastName(createAccountDto.lastName).bind() },
            { BirthDate(createAccountDto.birthDate).bind() },
            { Username(createAccountDto.username).bind() },
            { EmailAddress(createAccountDto.emailAddress).bind() },
            { Password(createAccountDto.password).bind() },
        ) { userId, firstName, lastName, birthDate, username, emailAddress, password ->
            UserProfileImpl(
                id = userId,
                UserDetails(
                    firstName = firstName,
                    lastName = lastName,
                    birthDate = birthDate,
                    username = username,
                    emailAddress = emailAddress,
                    password = password,
                    interest = createAccountDto.interest
                ),
                points = Points(0),
                threshold =
                    Threshold(
                        ThresholdValue(8.hours.inWholeMilliseconds),
                        NextReset(LocalDate.now().plusDays(7))
                    ),
                currentStreak =
                    Streak(userId, StreakValue(0), BeginDate(LocalDate.now()), EndDate(null))
            )
        }
    }
