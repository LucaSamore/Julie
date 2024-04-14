package com.example.domain.authentication

import arrow.core.Either
import com.example.data.Problem
import com.example.data.authentication.PasswordReset

interface PasswordResetUseCase {
    suspend operator fun invoke(emailAddress: String): Either<Problem, PasswordReset>
}
