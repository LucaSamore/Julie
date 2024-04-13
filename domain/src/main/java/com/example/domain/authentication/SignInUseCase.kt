package com.example.domain.authentication

import arrow.core.Either
import arrow.core.NonEmptyList
import com.example.data.Problem
import com.example.data.authentication.UserSignedIn

interface SignInUseCase {
    suspend operator fun invoke(
        emailAddress: String,
        password: String
    ): Either<NonEmptyList<Problem>, UserSignedIn>
}
