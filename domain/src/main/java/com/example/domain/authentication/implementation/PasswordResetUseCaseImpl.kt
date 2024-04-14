package com.example.domain.authentication.implementation

import arrow.core.Either
import arrow.core.raise.either
import com.example.data.Problem
import com.example.data.authentication.AuthenticationService
import com.example.data.authentication.PasswordReset
import com.example.data.user.EmailAddress
import com.example.data.user.UserProfileRepository
import com.example.domain.authentication.PasswordResetUseCase
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class PasswordResetUseCaseImpl
@Inject
constructor(
    private val authenticationService: AuthenticationService,
    private val userProfileRepository: UserProfileRepository,
    private val ioDispatcher: CoroutineDispatcher
) : PasswordResetUseCase {
    override suspend fun invoke(emailAddress: String): Either<Problem, PasswordReset> =
        withContext(ioDispatcher) {
            either {
                val email = EmailAddress(emailAddress).bind()
                val passwordResetEvent = authenticationService.passwordReset(email).bind()
                // TODO: Update user password in firestore
                passwordResetEvent
            }
        }
}
