package com.example.domain.authentication

import arrow.core.NonEmptySet
import com.example.data.user.Interest
import java.time.LocalDate

data class CreateAccountDto(
    val firstName: String,
    val lastName: String,
    val birthDate: LocalDate,
    val username: String,
    val emailAddress: String,
    val password: String,
    val interest: NonEmptySet<Interest>
)
