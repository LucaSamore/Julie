package com.example.data.user

import com.example.data.gamification.StreakDto
import com.example.data.gamification.ThresholdDto
import java.time.LocalDate

data class CreateAccountDto(
    val firstName: String,
    val lastName: String,
    val birthDate: LocalDate,
    val username: String,
    val emailAddress: String,
    val password: String,
    val interest: List<Interest>
)

data class UserProfileDto(
    val id: String,
    val firstName: String,
    val lastName: String,
    val birthDate: LocalDate,
    val username: String,
    val emailAddress: String,
    val password: String,
    val interest: List<Interest>,
    val points: Int,
    val threshold: ThresholdDto,
    val currentStreak: StreakDto,
)
