package com.example.domain.authentication

import arrow.core.NonEmptySet
import com.example.data.user.Interest
import java.time.LocalDate

sealed interface Credentials {
    val emailAddress: String
    val password: String

    data class SignInDto(override val emailAddress: String, override val password: String) :
        Credentials

    data class SignUpDto(
        override val emailAddress: String,
        override val password: String,
        val firstName: String,
        val lastName: String,
        val username: String,
        val birthDate: LocalDate,
        val interest: NonEmptySet<Interest>
    ) : Credentials
}