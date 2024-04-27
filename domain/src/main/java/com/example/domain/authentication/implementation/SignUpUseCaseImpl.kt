package com.example.domain.authentication.implementation

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.raise.either
import com.example.data.Problem
import com.example.data.authentication.AuthenticationService
import com.example.data.authentication.UserSignedUp
import com.example.data.authentication.ValidatedCredentials
import com.example.data.user.CreateAccountDto
import com.example.data.user.EmailAddress
import com.example.data.user.EmailAddressProblem
import com.example.data.user.UserProfile
import com.example.data.user.UserProfileRepository
import com.example.data.user.Username
import com.example.data.user.UsernameProblem
import com.example.data.user.implementation.createNewAccount
import com.example.data.util.accumulateIfLeft
import com.example.domain.authentication.SignUpUseCase
import com.example.domain.user.CacheUserIdUseCase
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class SignUpUseCaseImpl
@Inject
constructor(
    private val authenticationService: AuthenticationService,
    private val userProfileRepository: UserProfileRepository,
    private val ioDispatcher: CoroutineDispatcher,
    private val cacheUserIdUseCase: CacheUserIdUseCase,
) : SignUpUseCase {

    override suspend fun invoke(
        userData: CreateAccountDto
    ): Either<NonEmptyList<Problem>, UserSignedUp> =
        withContext(ioDispatcher) {
            either {
                val newUser = createNewAccount(userData).bind()
                checkEmailAddressAlreadyInUse(newUser.userDetails.emailAddress)
                    .accumulateIfLeft()
                    .bind()
                checkUsernameAlreadyInUse(newUser.userDetails.username).accumulateIfLeft().bind()
                storeNewAccount(newUser).accumulateIfLeft().bind()
                val signedUser = signUser(newUser).accumulateIfLeft().bind()
                authenticationService.sendVerificationEmail().accumulateIfLeft().bind()
                cacheUserIdUseCase(newUser.id.userId)
                signedUser
            }
        }

    private suspend fun checkEmailAddressAlreadyInUse(
        emailAddress: EmailAddress
    ): Either<Problem, Unit> = either {
        val emailAddressIsAlreadyInUse =
            userProfileRepository.isEmailAddressAlreadyInUse(emailAddress).bind()

        if (emailAddressIsAlreadyInUse) {
            raise(EmailAddressProblem("Email address already in use"))
        }
    }

    private suspend fun checkUsernameAlreadyInUse(username: Username): Either<Problem, Unit> =
        either {
            val usernameIsAlreadyInUse =
                userProfileRepository.isUsernameAlreadyInUse(username).bind()

            if (usernameIsAlreadyInUse) {
                raise(UsernameProblem("Username already in use"))
            }
        }

    private suspend fun storeNewAccount(userProfile: UserProfile): Either<Problem, UserProfile> =
        either {
            userProfileRepository.create(userProfile).bind()
        }

    private suspend fun signUser(userProfile: UserProfile): Either<Problem, UserSignedUp> = either {
        authenticationService
            .signUpWithEmailAndPassword(
                ValidatedCredentials.SignUpDto(
                    emailAddress = userProfile.userDetails.emailAddress,
                    password = userProfile.userDetails.password,
                    firstName = userProfile.userDetails.firstName,
                    lastName = userProfile.userDetails.lastName,
                    username = userProfile.userDetails.username,
                    birthDate = userProfile.userDetails.birthDate,
                    interest = userProfile.userDetails.interest
                )
            )
            .bind()
    }
}
