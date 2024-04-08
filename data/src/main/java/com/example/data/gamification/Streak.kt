package com.example.data.gamification

import java.time.LocalDate

@JvmInline
value class StreakValue(private val value: Int) {
    operator fun inc() = StreakValue(value + 1)
}

@JvmInline value class BeginDate(private val value: LocalDate)

@JvmInline value class EndDate(private val value: LocalDate?)

data class Streak(val value: StreakValue, val begin: BeginDate, val end: EndDate) {
    operator fun inc() = Streak(value.inc(), begin, end)
}
