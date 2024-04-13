package com.example.domain.authentication.implementation

import arrow.core.Either
import arrow.core.NonEmptyList
import com.example.data.Problem
import com.example.data.authentication.AuthenticationService
import com.example.data.authentication.UserSignedUp
import com.example.data.user.UserProfileRepository
import com.example.domain.authentication.CreateAccountDto
import com.example.domain.authentication.SignUpUseCase
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher

internal class SignUpUseCaseImpl
@Inject
constructor(
    private val authenticationService: AuthenticationService,
    private val userProfileRepository: UserProfileRepository,
    private val ioDispatcher: CoroutineDispatcher
) : SignUpUseCase {

    override suspend fun invoke(
        userData: CreateAccountDto
    ): Either<NonEmptyList<Problem>, UserSignedUp> {
        TODO("Not yet implemented")
    }
}
