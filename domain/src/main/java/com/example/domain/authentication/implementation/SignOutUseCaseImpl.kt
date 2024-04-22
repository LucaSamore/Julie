package com.example.domain.authentication.implementation

import arrow.core.Either
import com.example.data.WorkerManager
import com.example.data.authentication.AuthenticationProblem
import com.example.data.authentication.AuthenticationService
import com.example.data.authentication.UserSignedOut
import com.example.domain.authentication.SignOutUseCase
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class SignOutUseCaseImpl
@Inject
constructor(
    private val authenticationService: AuthenticationService,
    private val ioDispatcher: CoroutineDispatcher,
    private val workerManager: WorkerManager
) : SignOutUseCase {

    override suspend fun invoke(): Either<AuthenticationProblem, UserSignedOut> =
        withContext(ioDispatcher) {
            val userSignedOutEvent = authenticationService.signOut()
            workerManager.cancelAllWorkers()
            userSignedOutEvent
        }
}
