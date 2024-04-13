package com.example.domain.authentication.implementation

import arrow.core.Either
import arrow.core.NonEmptyList
import com.example.data.Problem
import com.example.data.authentication.AuthenticationService
import com.example.data.authentication.UserSignedIn
import com.example.domain.authentication.Credentials
import com.example.domain.authentication.SignInUseCase
import javax.inject.Inject

internal class SignInUseCaseImpl @Inject constructor(private val authenticationService: AuthenticationService): SignInUseCase {
    override fun invoke(signInCredentials: Credentials.SignInDto): Either<NonEmptyList<Problem>, UserSignedIn> {
        TODO("Not yet implemented")
    }
}