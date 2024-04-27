package com.example.data.user.implementation

import com.example.data.gamification.Streak
import com.example.data.gamification.Threshold
import com.example.data.user.Interest
import com.example.data.user.UserProfile
import com.example.data.util.prettyFormat
import java.time.LocalDateTime

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
    val threshold: FirestoreThresholdDto? = null,
    val currentStreak: FirestoreCurrentStreakDto? = null,
    val createdAt: String? = LocalDateTime.now().prettyFormat()
) {
    companion object {
        const val COLLECTION = "users"

        fun fromEntity(user: UserProfile): FirestoreUserDto =
            FirestoreUserDto(
                id = user.id.userId,
                firstName = user.userDetails.firstName.firstName,
                lastName = user.userDetails.lastName.lastName,
                birthDate = user.userDetails.birthDate.birthDate.toString(),
                username = user.userDetails.username.username,
                emailAddress = user.userDetails.emailAddress.emailAddress,
                password = user.userDetails.password.password,
                interest = user.userDetails.interest.map { FirestoreInterestDto.fromEntity(it) },
                points = user.points.points,
                threshold = FirestoreThresholdDto.fromEntity(user.threshold),
                currentStreak = FirestoreCurrentStreakDto.fromEntity(user.currentStreak)
            )
    }
}

internal data class FirestoreThresholdDto(
    val valueInMillis: Long? = null,
    val nextReset: String? = null
) {
    companion object {
        fun fromEntity(threshold: Threshold): FirestoreThresholdDto =
            FirestoreThresholdDto(
                valueInMillis = threshold.valueInMillis.valueInMillis,
                nextReset = threshold.nextReset.nextReset.toString()
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
                started = currentStreak.begin.value.toString(),
                ended = currentStreak.end.value?.toString()
            )
    }
}

internal data class FirestoreInterestDto(val name: String? = null, val category: String? = null) {
    companion object {
        fun fromEntity(interest: Interest): FirestoreInterestDto =
            FirestoreInterestDto(name = interest.name.name, category = interest.category.category)
    }
}
