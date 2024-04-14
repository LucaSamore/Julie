package com.example.data.gamification

@JvmInline
value class Points(val points: Int) : Comparable<Int> {
    operator fun plus(other: Points) = Points(points + other.points)

    operator fun minus(other: Points) = Points(points - other.points)

    operator fun times(other: Points) = Points(points * other.points)

    operator fun div(other: Points) = Points(points / other.points)

    override fun compareTo(other: Int): Int = points.compareTo(other)
}
