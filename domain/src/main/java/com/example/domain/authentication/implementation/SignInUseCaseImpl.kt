package com.example.domain.authentication.implementation

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.raise.either
import arrow.core.raise.zipOrAccumulate
import com.example.data.Problem
import com.example.data.WorkerManager
import com.example.data.authentication.AuthenticationService
import com.example.data.authentication.EmailNotVerified
import com.example.data.authentication.UserSignedIn
import com.example.data.authentication.ValidatedCredentials
import com.example.data.user.EmailAddress
import com.example.data.user.Password
import com.example.data.user.UserProfileRepository
import com.example.data.user.implementation.UserDatastore
import com.example.data.util.accumulateIfLeft
import com.example.data.util.dateTimeOfRecording
import com.example.domain.authentication.SignInUseCase
import com.example.domain.user.CacheUserIdUseCase
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class SignInUseCaseImpl
@Inject
constructor(
    private val authenticationService: AuthenticationService,
    private val userProfileRepository: UserProfileRepository,
    private val ioDispatcher: CoroutineDispatcher,
    private val cacheUserIdUseCase: CacheUserIdUseCase,
    private val userDatastore: UserDatastore,
    private val workerManager: WorkerManager
) : SignInUseCase {
    override suspend fun invoke(
        emailAddress: String,
        password: String
    ): Either<NonEmptyList<Problem>, UserSignedIn> =
        withContext(ioDispatcher) {
            either {
                val validatedCredentials = validateCredentials(emailAddress, password).bind()
                val signedInEvent =
                    authenticationService
                        .signInWithEmailAndPassword(validatedCredentials)
                        .accumulateIfLeft()
                        .bind()
                checkIfEmailIsVerified().accumulateIfLeft().bind()
                val userId =
                    userProfileRepository
                        .getUserIdByEmailAddress(validatedCredentials.emailAddress)
                        .accumulateIfLeft()
                        .bind()
                cacheUserIdUseCase(userId.userId)
                userDatastore.saveDateTimeOfRecordingToDataStore(dateTimeOfRecording)
                workerManager.scheduleUploadReportWorker()
                workerManager.scheduleDailyChallengeWorker()
                signedInEvent
            }
        }

    private fun validateCredentials(
        emailAddress: String,
        password: String
    ): Either<NonEmptyList<Problem>, ValidatedCredentials.SignInDto> = either {
        zipOrAccumulate(
            { EmailAddress(emailAddress).bind() },
            { Password(password).bind() },
        ) { emailAddress, password ->
            ValidatedCredentials.SignInDto(emailAddress, password)
        }
    }

    private fun checkIfEmailIsVerified(): Either<Problem, Unit> = either {
        if (!authenticationService.isEmailVerified()) {
            raise(EmailNotVerified("Email is not verified"))
        }
    }
}
