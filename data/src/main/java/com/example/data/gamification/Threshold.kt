package com.example.data.gamification

import java.time.LocalDate

data class Threshold(val valueInMillis: ThresholdValue, val nextReset: NextReset)

@JvmInline value class ThresholdValue(val valueInMillis: Long)

@JvmInline value class NextReset(val nextReset: LocalDate)
