package com.example.data.gamification

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import com.example.data.Problem

@JvmInline
value class Points private constructor(val value: Int) : Comparable<Int> {
    operator fun plus(other: Points) = Points(value + other.value)

    operator fun minus(other: Points) = Points(value - other.value)

    operator fun times(other: Points) = Points(value * other.value)

    operator fun div(other: Points) = Points(value / other.value)

    override fun compareTo(other: Int): Int = value.compareTo(other)

    companion object {
        operator fun invoke(points: Int): Either<GamificationProblem, Points> = either {
            ensure(points >= 0) { PointsProblem("Points must be positive") }
            Points(points)
        }
    }
}

sealed interface GamificationProblem : Problem

@JvmInline value class PointsProblem(override val message: String) : GamificationProblem
