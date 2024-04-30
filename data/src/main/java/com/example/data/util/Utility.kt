package com.example.data.util

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.nonEmptyListOf
import com.example.data.Problem
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun <T> Either<Problem, T>.accumulateIfLeft(): Either<NonEmptyList<Problem>, T> = mapLeft {
    nonEmptyListOf(it)
}

fun LocalDateTime.prettyFormat(pattern: String = "yyyy-MM-dd HH:mm:ss"): String =
    format(DateTimeFormatter.ofPattern(pattern))

fun today(): LocalDate = LocalDate.now()

fun Long.inMinutes(): Double = toDouble() / 60_000

val dateTimeOfRecording: LocalDateTime =
    LocalDateTime.now().withHour(23).withMinute(55).withSecond(0)
