package com.example.data.gamification

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import com.example.data.Problem

@JvmInline
value class Points private constructor(val points: Int) : Comparable<Int> {
    operator fun plus(other: Points) = Points(points + other.points)

    operator fun minus(other: Points) = Points(points - other.points)

    operator fun times(other: Points) = Points(points * other.points)

    operator fun div(other: Points) = Points(points / other.points)

    override fun compareTo(other: Int): Int = points.compareTo(other)

    companion object {
        operator fun invoke(points: Int): Either<GamificationProblem, Points> = either {
            ensure(points >= 0) { PointsProblem("Points must be positive") }
            Points(points)
        }
    }
}

sealed interface GamificationProblem : Problem

@JvmInline value class PointsProblem(override val message: String) : GamificationProblem
