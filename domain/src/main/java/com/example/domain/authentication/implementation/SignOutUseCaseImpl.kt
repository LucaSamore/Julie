package com.example.domain.authentication.implementation

import arrow.core.Either
import com.example.data.authentication.AuthenticationProblem
import com.example.data.authentication.AuthenticationService
import com.example.data.authentication.UserSignedOut
import com.example.data.user.implementation.UserDatastore
import com.example.domain.authentication.SignOutUseCase
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class SignOutUseCaseImpl
@Inject
constructor(
    private val authenticationService: AuthenticationService,
    private val ioDispatcher: CoroutineDispatcher,
    private val userDatastore: UserDatastore
) : SignOutUseCase {

    override suspend fun invoke(): Either<AuthenticationProblem, UserSignedOut> =
        withContext(ioDispatcher) { authenticationService.signOut() }
}
