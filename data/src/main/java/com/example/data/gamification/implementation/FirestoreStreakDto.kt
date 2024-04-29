package com.example.data.gamification.implementation

import com.example.data.gamification.Streak

internal data class FirestoreStreakDto(
    val userId: String? = null,
    val value: Int? = null,
    val started: String? = null,
    val ended: String? = null
) {
    companion object {
        const val COLLECTION = "streaks"

        fun fromEntity(streak: Streak): FirestoreStreakDto =
            FirestoreStreakDto(
                userId = streak.userId.userId,
                value = streak.value.value,
                started = streak.begin.value.toString(),
                ended = streak.end.value.toString()
            )
    }
}
