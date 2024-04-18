package com.example.domain.authentication.implementation

import arrow.core.Either
import com.example.data.Problem
import com.example.data.authentication.AuthenticationService
import com.example.data.authentication.VerificationEmailSent
import com.example.domain.authentication.VerifyEmailUseCase
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher

internal class VerifyEmailUseCaseImpl
@Inject
constructor(
    private val authenticationService: AuthenticationService,
    private val ioDispatcher: CoroutineDispatcher
) : VerifyEmailUseCase {

    override suspend fun invoke(emailAddress: String): Either<Problem, VerificationEmailSent> {
        TODO("Not yet implemented")
    }
}
