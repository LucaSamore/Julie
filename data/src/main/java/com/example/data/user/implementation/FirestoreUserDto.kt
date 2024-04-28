package com.example.data.user.implementation

import android.util.Log
import com.example.data.gamification.Streak
import com.example.data.gamification.StreakDto
import com.example.data.gamification.Threshold
import com.example.data.gamification.ThresholdDto
import com.example.data.user.Category
import com.example.data.user.Interest
import com.example.data.user.Name
import com.example.data.user.UserProfile
import com.example.data.user.UserProfileDto
import com.example.data.util.prettyFormat
import com.example.data.util.today
import java.time.LocalDate
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

        private const val TAG = "FirestoreUserDto"

        fun fromEntity(user: UserProfile): FirestoreUserDto {
            return FirestoreUserDto(
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

        fun toEntity(firestoreUserDto: FirestoreUserDto): UserProfile? {
            return createUserProfile(
                    UserProfileDto(
                        id = firestoreUserDto.id ?: "",
                        firstName = firestoreUserDto.firstName ?: "",
                        lastName = firestoreUserDto.lastName ?: "",
                        birthDate =
                            LocalDate.parse(firestoreUserDto.birthDate ?: today().toString()),
                        username = firestoreUserDto.username ?: "",
                        emailAddress = firestoreUserDto.emailAddress ?: "",
                        password = firestoreUserDto.password ?: "",
                        interest =
                            firestoreUserDto.interest?.map {
                                Interest(
                                    name = Name(it.name ?: ""),
                                    category = Category(it.category ?: "")
                                )
                            } ?: emptyList(),
                        points = firestoreUserDto.points ?: -1,
                        threshold =
                            ThresholdDto(
                                valueInMillis = firestoreUserDto.threshold?.valueInMillis ?: -1,
                                nextReset =
                                    LocalDate.parse(
                                        firestoreUserDto.threshold?.nextReset ?: today().toString()
                                    )
                            ),
                        currentStreak =
                            StreakDto(
                                value = firestoreUserDto.currentStreak?.value ?: -1,
                                begin =
                                    LocalDate.parse(
                                        firestoreUserDto.currentStreak?.started
                                            ?: today().plusDays(1).toString()
                                    ),
                                end =
                                    if (firestoreUserDto.currentStreak?.ended != null)
                                        LocalDate.parse(firestoreUserDto.currentStreak.ended)
                                    else null
                            )
                    )
                )
                .fold(
                    {
                        it.forEach { error -> Log.e(TAG, error.message) }
                        null
                    },
                    { it }
                )
        }
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
                ended = currentStreak.end?.value?.toString()
            )
    }
}

internal data class FirestoreInterestDto(val name: String? = null, val category: String? = null) {
    companion object {
        fun fromEntity(interest: Interest): FirestoreInterestDto =
            FirestoreInterestDto(name = interest.name.name, category = interest.category.category)
    }
}
