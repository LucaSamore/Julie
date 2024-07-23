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
import com.example.data.today
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
import java.util.UUID
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds

internal data class UserProfileImpl(
    override val id: UserId,
    override val userDetails: UserDetails,
    override val points: Points,
    override val threshold: Threshold,
    override val currentStreak: Streak
) : UserProfile {

    override fun addPoints(pointsToAdd: Points): UserProfile = copy(points = points + pointsToAdd)

    override fun resetPoints(): UserProfile = copy(points = Points(0).getOrNull() ?: points)

    override fun resetThreshold(): UserProfile = copy(threshold = createDefaultThreshold())

    override fun increaseThreshold(): UserProfile = updateThreshold { it.plus(1.hours) }

    override fun decreaseThreshold(): UserProfile = updateThreshold { it.minus(1.hours) }

    override fun incrementCurrentStreak(): UserProfile =
        copy(currentStreak = currentStreak.copy(value = currentStreak.value + 1))

    override fun resetStreak(): UserProfile = copy(currentStreak = createInitialStreak())

    override fun endCurrentStreak(): UserProfile =
        copy(
            currentStreak =
                currentStreak.copy(end = EndDate(today()).getOrNull() ?: currentStreak.end)
        )

    private fun createDefaultThreshold(): Threshold {
        val thresholdValue =
            ThresholdValue(DEFAULT_THRESHOLD.hours.inWholeMilliseconds).getOrNull()
                ?: return threshold
        val nextReset = NextReset(today().plusDays(DAYS_UNTIL_NEXT_RESET))
        return Threshold(thresholdValue, nextReset)
    }

    private fun updateThreshold(update: (Duration) -> Duration): UserProfile {
        val newValueInMillis =
            ThresholdValue(update(threshold.valueInMillis.value.milliseconds).inWholeMilliseconds)
                .getOrNull() ?: return this
        return copy(threshold = threshold.copy(valueInMillis = newValueInMillis))
    }

    private fun createInitialStreak(): Streak {
        val begin = BeginDate(today()).getOrNull() ?: currentStreak.begin
        val end = EndDate(null).getOrNull() ?: currentStreak.end
        val value = StreakValue(0).getOrNull() ?: currentStreak.value
        return Streak(id, value, begin, end)
    }

    companion object {
        private const val DEFAULT_THRESHOLD = 8
        private const val DAYS_UNTIL_NEXT_RESET = 7L
    }
}

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
            { StreakValue(0).bind() },
            { BeginDate(today()).bind() },
            { EndDate(null).bind() },
        ) { points, thresholdValue, streakValue, beginDate, endDate ->
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
                threshold = Threshold(thresholdValue, NextReset(today().plusDays(7))),
                currentStreak = Streak(userId, streakValue, beginDate, endDate)
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
                { StreakValue(userProfileDto.currentStreak.value).bind() },
                { BeginDate(userProfileDto.currentStreak.begin).bind() },
                { EndDate(userProfileDto.currentStreak.end).bind() }
            ) { points, thresholdValue, streakValue, beginDate, endDate ->
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
                    threshold =
                        Threshold(thresholdValue, NextReset(userProfileDto.threshold.nextReset)),
                    currentStreak = Streak(userId, streakValue, beginDate, endDate)
                )
            }
        }
    }
