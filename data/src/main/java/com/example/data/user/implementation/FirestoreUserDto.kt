package com.example.data.user.implementation

import com.example.data.gamification.Streak
import com.example.data.user.Interest
import com.example.data.user.UserProfile
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

internal data class FirestoreUserDto(
    val id: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val birthDate: String? = null,
    val username: String? = null,
    val emailAddress: String? = null,
    val password: String? = null,
    val interest: List<FirestoreInterestDto>? = null,
    val points: Int? = null,
    val currentStreak: FirestoreCurrentStreakDto? = null,
    val createdAt: String? = LocalDateTime.now().betterFormat()
) {
    companion object {
        const val COLLECTION = "users"

        fun fromEntity(user: UserProfile): FirestoreUserDto =
            FirestoreUserDto(
                id = user.id.userId,
                firstName = user.userDetails.firstName.firstName,
                lastName = user.userDetails.lastName.lastName,
                birthDate = user.userDetails.birthDate.birthDate.betterFormat(),
                username = user.userDetails.username.username,
                emailAddress = user.userDetails.emailAddress.emailAddress,
                password = user.userDetails.password.password,
                interest = user.userDetails.interest.map { FirestoreInterestDto.fromEntity(it) },
                points = user.points.points,
                currentStreak = FirestoreCurrentStreakDto.fromEntity(user.currentStreak)
            )
    }
}

internal data class FirestoreCurrentStreakDto(
    val value: Int? = null,
    val started: String? = null,
    val ended: String? = null
) {
    companion object {
        fun fromEntity(currentStreak: Streak): FirestoreCurrentStreakDto =
            FirestoreCurrentStreakDto(
                value = currentStreak.value.value,
                started = currentStreak.begin.value.betterFormat(),
                ended = currentStreak.end.value?.betterFormat()
            )
    }
}

internal data class FirestoreInterestDto(val name: String? = null, val category: String? = null) {
    companion object {
        fun fromEntity(interest: Interest): FirestoreInterestDto =
            FirestoreInterestDto(name = interest.name.name, category = interest.category.category)
    }
}

private fun LocalDate.betterFormat(pattern: String = "yyyy-MM-dd"): String =
    format(DateTimeFormatter.ofPattern(pattern))

private fun LocalDateTime.betterFormat(pattern: String = "yyyy-MM-dd"): String =
    format(DateTimeFormatter.ofPattern(pattern))
