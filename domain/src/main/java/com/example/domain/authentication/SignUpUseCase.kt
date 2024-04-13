package com.example.domain.authentication

import arrow.core.Either
import arrow.core.NonEmptyList
import com.example.data.Problem
import com.example.data.authentication.UserSignedUp

interface SignUpUseCase {
    suspend operator fun invoke(
        userData: CreateAccountDto
    ): Either<NonEmptyList<Problem>, UserSignedUp>
}
