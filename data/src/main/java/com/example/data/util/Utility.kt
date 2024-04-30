package com.example.data.util

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.nonEmptyListOf
import com.example.data.Problem
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun <T> Either<Problem, T>.accumulateIfLeft(): Either<NonEmptyList<Problem>, T> = mapLeft {
    nonEmptyListOf(it)
}

fun LocalDateTime.prettyFormat(pattern: String = "yyyy-MM-dd HH:mm:ss"): String =
    format(DateTimeFormatter.ofPattern(pattern))

fun today(): LocalDate = LocalDate.now()

fun Long.inMinutes(): Double = toDouble() / 60_000

fun dateTimeOfRecording(): LocalDateTime {
    val now = LocalDateTime.now()
    val targetDate = if (now.hour <= 23) now.toLocalDate() else now.plusDays(1).toLocalDate()
    return targetDate.atTime(23, 0)
}

fun millisecondsUntil11PM(): Long {
    val now = LocalDateTime.now()
    val today11PM = LocalDateTime.of(now.year, now.month, now.dayOfMonth, 23, 0, 0)

    val tomorrow11PM =
        if (now.hour >= 23) {
            today11PM.plusDays(1)
        } else {
            today11PM
        }

    return if (now.isBefore(today11PM)) {
        now.until(today11PM, ChronoUnit.MILLIS)
    } else {
        now.until(tomorrow11PM, ChronoUnit.MILLIS)
    }
}
