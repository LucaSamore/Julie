package com.example.data.gamification

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import com.example.data.user.UserId
import com.example.data.util.today
import java.time.LocalDate

data class Streak(
    val userId: UserId,
    val value: StreakValue,
    val begin: BeginDate,
    val end: EndDate?
)

data class StreakDto(val value: Int, val begin: LocalDate, val end: LocalDate?)

@JvmInline
value class StreakValue private constructor(val value: Int) {
    operator fun plus(other: Int): StreakValue = StreakValue(value + other)

    companion object {
        operator fun invoke(value: Int): Either<GamificationProblem, StreakValue> = either {
            ensure(value >= 0) { StreakValueProblem("Streak value must be positive") }
            StreakValue(value)
        }
    }
}

@JvmInline
value class BeginDate private constructor(val value: LocalDate) {
    companion object {
        operator fun invoke(value: LocalDate): Either<GamificationProblem, BeginDate> = either {
            ensure(value.isBefore(today().plusDays(1))) {
                BeginDateProblem("Begin date cannot be in the future")
            }
            BeginDate(value)
        }
    }
}

@JvmInline
value class EndDate private constructor(val value: LocalDate) {
    companion object {
        operator fun invoke(value: LocalDate): Either<GamificationProblem, EndDate> = either {
            ensure(value.isBefore(today().plusDays(1))) {
                EndDateProblem("End date cannot be in the future")
            }
            EndDate(value)
        }
    }
}

@JvmInline value class StreakValueProblem(override val message: String) : GamificationProblem

@JvmInline value class BeginDateProblem(override val message: String) : GamificationProblem

@JvmInline value class EndDateProblem(override val message: String) : GamificationProblem
