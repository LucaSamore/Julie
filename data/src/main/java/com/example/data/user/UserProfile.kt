package com.example.data.user

import arrow.core.NonEmptySet
import com.example.data.gamification.Points
import com.example.data.gamification.Streak

interface UserProfile {
    val userId: UserId

    val firstName: FirstName

    val lastName: LastName

    val userName: Username

    val emailAddress: EmailAddress

    val password: Password

    val interest: NonEmptySet<Interest>

    val points: Points

    val currentStreak: Streak

    val pastStreaks: PastStreaks
}

@JvmInline value class UserId(private val userId: String)

@JvmInline value class FirstName(private val firstName: String)

@JvmInline value class LastName(private val lastName: String)

@JvmInline value class Username(private val username: String)

@JvmInline value class EmailAddress(private val emailAddress: String)

@JvmInline value class Password(private val password: String)

data class Interest(val name: Name, val category: Category)

@JvmInline value class Name(private val name: String)

@JvmInline value class Category(private val category: String)

@JvmInline value class PastStreaks(private val pastStreaks: Sequence<Streak>)
