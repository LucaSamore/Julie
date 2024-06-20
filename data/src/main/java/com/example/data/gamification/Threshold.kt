package com.example.data.gamification

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import java.time.LocalDate

data class Threshold(val valueInMillis: ThresholdValue, val nextReset: NextReset)

data class ThresholdDto(val valueInMillis: Long, val nextReset: LocalDate)

@JvmInline
value class ThresholdValue private constructor(val valueInMillis: Long) {
    companion object {
        operator fun invoke(valueInMillis: Long): Either<GamificationProblem, ThresholdValue> =
            either {
                ensure(valueInMillis >= 0) {
                    ThresholdValueProblem("Threshold value must be positive")
                }
                ThresholdValue(valueInMillis)
            }
    }
}

@JvmInline value class NextReset(val nextReset: LocalDate)

@JvmInline value class ThresholdValueProblem(override val message: String) : GamificationProblem
