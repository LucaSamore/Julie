package com.example.domain.authentication

import arrow.core.Either
import arrow.core.NonEmptyList
import com.example.data.Problem
import com.example.data.authentication.UserSignedUp
import com.example.data.user.CreateAccountDto

interface SignUpUseCase {
    suspend operator fun invoke(
        userData: CreateAccountDto
    ): Either<NonEmptyList<Problem>, UserSignedUp>
}
