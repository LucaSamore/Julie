package com.example.domain.authentication

import arrow.core.Either
import com.example.data.authentication.AuthenticationProblem
import com.example.data.authentication.UserSignedOut

interface SignOutUseCase {
    suspend operator fun invoke(): Either<AuthenticationProblem, UserSignedOut>
}
