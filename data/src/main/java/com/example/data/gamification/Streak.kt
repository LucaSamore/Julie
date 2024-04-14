package com.example.data.gamification

import com.example.data.user.UserId
import java.time.LocalDate

@JvmInline
value class StreakValue(val value: Int) {
    operator fun plus(other: Int): StreakValue = StreakValue(value + other)
}

@JvmInline value class BeginDate(val value: LocalDate)

@JvmInline value class EndDate(val value: LocalDate?)

data class Streak(
    val userId: UserId,
    val value: StreakValue,
    val begin: BeginDate,
    val end: EndDate
) {
    infix fun incrementedBy(amount: Int): Streak = copy(value = value + amount)

    infix fun ends(endDate: LocalDate): Streak = copy(end = EndDate(endDate))
}

fun today(): LocalDate = LocalDate.now()
