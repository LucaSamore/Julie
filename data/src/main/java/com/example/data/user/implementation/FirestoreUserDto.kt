package com.example.data.user.implementation

import arrow.core.Either
import arrow.core.NonEmptyList
import com.example.data.Problem
import com.example.data.gamification.Streak
import com.example.data.gamification.StreakDto
import com.example.data.gamification.Threshold
import com.example.data.gamification.ThresholdDto
import com.example.data.prettyFormat
import com.example.data.today
import com.example.data.user.Category
import com.example.data.user.Interest
import com.example.data.user.Name
import com.example.data.user.UserProfile
import com.example.data.user.UserProfileDto
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

        fun fromEntity(user: UserProfile): FirestoreUserDto {
            return FirestoreUserDto(
                id = user.id.value,
                firstName = user.userDetails.firstName.value,
                lastName = user.userDetails.lastName.value,
                birthDate = user.userDetails.birthDate.value.toString(),
                username = user.userDetails.username.value,
                emailAddress = user.userDetails.emailAddress.value,
                password = user.userDetails.password.value,
                interest = user.userDetails.interest.map { FirestoreInterestDto.fromEntity(it) },
                points = user.points.value,
                threshold = FirestoreThresholdDto.fromEntity(user.threshold),
                currentStreak = FirestoreCurrentStreakDto.fromEntity(user.currentStreak)
            )
        }

        fun toEntity(
            firestoreUserDto: FirestoreUserDto
        ): Either<NonEmptyList<Problem>, UserProfile> {
            return createUserProfile(
                UserProfileDto(
                    id = firestoreUserDto.id ?: "",
                    firstName = firestoreUserDto.firstName ?: "",
                    lastName = firestoreUserDto.lastName ?: "",
                    birthDate = LocalDate.parse(firestoreUserDto.birthDate ?: today().toString()),
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
        }

        fun entityToDocumentFields(entity: UserProfile) =
            mapOf(
                "firstName" to entity.userDetails.firstName.value,
                "lastName" to entity.userDetails.lastName.value,
                "birthDate" to entity.userDetails.birthDate.value.toString(),
                "username" to entity.userDetails.username.value,
                "emailAddress" to entity.userDetails.emailAddress.value,
                "password" to entity.userDetails.password.value,
                "points" to entity.points.value,
                "threshold.valueInMillis" to entity.threshold.valueInMillis.value,
                "threshold.nextReset" to entity.threshold.nextReset.value.toString(),
                "currentStreak.value" to entity.currentStreak.value.value,
                "currentStreak.started" to entity.currentStreak.begin.value.toString(),
                "currentStreak.ended" to
                    if (entity.currentStreak.end.value == null) null
                    else entity.currentStreak.end.value.toString()
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
                valueInMillis = threshold.valueInMillis.value,
                nextReset = threshold.nextReset.value.toString()
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
            FirestoreInterestDto(name = interest.name.value, category = interest.category.value)
    }
}
