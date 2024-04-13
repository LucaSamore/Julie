package com.example.data.user

import java.time.LocalDate

data class CreateAccountDto(
    val firstName: String,
    val lastName: String,
    val birthDate: LocalDate,
    val username: String,
    val emailAddress: String,
    val password: String,
    val interest: List<Interest>
)
