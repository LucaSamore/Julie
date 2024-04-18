package com.example.data.authentication

import arrow.core.Either
import com.example.data.Problem
import com.example.data.user.BirthDate
import com.example.data.user.EmailAddress
import com.example.data.user.FirstName
import com.example.data.user.Interest
import com.example.data.user.LastName
import com.example.data.user.Password
import com.example.data.user.Username

interface AuthenticationService {
    suspend fun signInWithEmailAndPassword(
        signInCredentials: ValidatedCredentials.SignInDto
    ): Either<AuthenticationProblem, UserSignedIn>

    suspend fun signUpWithEmailAndPassword(
        signUpCredentials: ValidatedCredentials.SignUpDto
    ): Either<AuthenticationProblem, UserSignedUp>

    suspend fun passwordReset(
        emailAddress: EmailAddress
    ): Either<AuthenticationProblem, PasswordReset>

    suspend fun signOut(): Either<AuthenticationProblem, UserSignedOut>

    suspend fun sendVerificationEmail(): Either<AuthenticationProblem, VerificationEmailSent>

    fun isEmailVerified(): Boolean

    fun isUserLoggedIn(): Boolean
}

sealed interface ValidatedCredentials {
    val emailAddress: EmailAddress
    val password: Password

    data class SignInDto(override val emailAddress: EmailAddress, override val password: Password) :
        ValidatedCredentials

    data class SignUpDto(
        override val emailAddress: EmailAddress,
        override val password: Password,
        val firstName: FirstName,
        val lastName: LastName,
        val username: Username,
        val birthDate: BirthDate,
        val interest: List<Interest>
    ) : ValidatedCredentials
}

data object UserSignedIn

data object UserSignedUp

data object UserSignedOut

data object PasswordReset

data object VerificationEmailSent

sealed interface AuthenticationProblem : Problem

@JvmInline
value class AuthenticationError(override val message: String) : AuthenticationProblem {
    companion object {
        fun fromThrowable(throwable: Throwable): AuthenticationError =
            AuthenticationError(throwable.message ?: UnknownError)
    }
}

@JvmInline value class EmailNotVerified(override val message: String) : AuthenticationProblem

const val UnknownError = "Unknown error"
