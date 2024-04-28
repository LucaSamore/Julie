package com.example.data.user.implementation

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.raise.either
import arrow.core.raise.zipOrAccumulate
import com.example.data.Problem
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
import com.example.data.user.UserProfile
import com.example.data.user.UserProfileDto
import com.example.data.user.Username
import com.example.data.util.today
import java.util.UUID
import kotlin.time.Duration.Companion.hours

internal data class UserProfileImpl(
    override val id: UserId,
    override val userDetails: UserDetails,
    override val points: Points,
    override val threshold: Threshold,
    override val currentStreak: Streak
) : UserProfile

fun createNewAccount(
    createAccountDto: CreateAccountDto
): Either<NonEmptyList<Problem>, UserProfile> = either {
    zipOrAccumulate(
        { UserId(UUID.randomUUID().toString()).bind() },
        { FirstName(createAccountDto.firstName).bind() },
        { LastName(createAccountDto.lastName).bind() },
        { BirthDate(createAccountDto.birthDate).bind() },
        { Username(createAccountDto.username).bind() },
        { EmailAddress(createAccountDto.emailAddress).bind() },
        { Password(createAccountDto.password).bind() },
    ) { userId, firstName, lastName, birthDate, username, emailAddress, password ->
        zipOrAccumulate(
            { Points(0).bind() },
            { ThresholdValue(8.hours.inWholeMilliseconds).bind() },
            { NextReset(today().plusDays(7)).bind() },
            { StreakValue(0).bind() },
            { BeginDate(today()).bind() },
        ) { points, thresholdValue, nextReset, streakValue, beginDate ->
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
                points = points,
                threshold = Threshold(thresholdValue, nextReset),
                currentStreak = Streak(userId, streakValue, beginDate, null)
            )
        }
    }
}

fun createUserProfile(userProfileDto: UserProfileDto): Either<NonEmptyList<Problem>, UserProfile> =
    either {
        zipOrAccumulate(
            { UserId(userProfileDto.id).bind() },
            { FirstName(userProfileDto.firstName).bind() },
            { LastName(userProfileDto.lastName).bind() },
            { BirthDate(userProfileDto.birthDate).bind() },
            { Username(userProfileDto.username).bind() },
            { EmailAddress(userProfileDto.emailAddress).bind() },
            { Password(userProfileDto.password).bind() },
        ) { userId, firstName, lastName, birthDate, username, emailAddress, password ->
            zipOrAccumulate(
                { Points(userProfileDto.points).bind() },
                { ThresholdValue(userProfileDto.threshold.valueInMillis).bind() },
                { NextReset(userProfileDto.threshold.nextReset).bind() },
                { StreakValue(userProfileDto.currentStreak.value).bind() },
                { BeginDate(userProfileDto.currentStreak.begin).bind() },
                { EndDate(userProfileDto.currentStreak.end).bind() }
            ) { points, thresholdValue, nextReset, streakValue, beginDate, endDate ->
                UserProfileImpl(
                    id = userId,
                    UserDetails(
                        firstName = firstName,
                        lastName = lastName,
                        birthDate = birthDate,
                        username = username,
                        emailAddress = emailAddress,
                        password = password,
                        interest = userProfileDto.interest
                    ),
                    points = points,
                    threshold = Threshold(thresholdValue, nextReset),
                    currentStreak = Streak(userId, streakValue, beginDate, endDate)
                )
            }
        }
    }
