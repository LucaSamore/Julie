package com.example.domain.authentication.implementation

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.nonEmptyListOf
import arrow.core.raise.either
import com.example.data.Problem
import com.example.data.authentication.AuthenticationService
import com.example.data.authentication.UserSignedUp
import com.example.data.authentication.ValidatedCredentials
import com.example.data.user.CreateAccountDto
import com.example.data.user.UserProfileRepository
import com.example.data.user.implementation.createNewAccount
import com.example.domain.authentication.SignUpUseCase
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class SignUpUseCaseImpl
@Inject
constructor(
    private val authenticationService: AuthenticationService,
    private val userProfileRepository: UserProfileRepository,
    private val ioDispatcher: CoroutineDispatcher
) : SignUpUseCase {

    override suspend fun invoke(
        userData: CreateAccountDto
    ): Either<NonEmptyList<Problem>, UserSignedUp> =
        withContext(ioDispatcher) {
            either {
                val newUser = createNewAccount(userData).bind()
                val signedUser =
                    authenticationService
                        .signUpWithEmailAndPassword(
                            ValidatedCredentials.SignUpDto(
                                emailAddress = newUser.userDetails.emailAddress,
                                password = newUser.userDetails.password,
                                firstName = newUser.userDetails.firstName,
                                lastName = newUser.userDetails.lastName,
                                username = newUser.userDetails.username,
                                birthDate = newUser.userDetails.birthDate,
                                interest = newUser.userDetails.interest
                            )
                        )
                        .mapLeft { nonEmptyListOf(it) }
                        .bind()
                userProfileRepository.create(newUser).mapLeft { nonEmptyListOf(it) }.bind()
                signedUser
            }
        }
}
