package com.example.domain.authentication

import arrow.core.Either
import arrow.core.NonEmptyList
import com.example.data.Problem
import com.example.data.authentication.UserSignedIn

interface SignInUseCase {
    operator fun invoke(signInCredentials: Credentials.SignInDto): Either<NonEmptyList<Problem>, UserSignedIn>
}