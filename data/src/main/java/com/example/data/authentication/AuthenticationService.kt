package com.example.data.authentication

import arrow.core.Either
import arrow.core.NonEmptySet
import com.example.data.user.Interest
import java.time.LocalDate

interface AuthenticationService {
    suspend fun signInWithEmailAndPassword(
        signInCredentials: Credentials.SignInDto
    ): Either<AuthenticationError, UserSignedIn>

    suspend fun signUpWithEmailAndPassword(
        signInCredentials: Credentials.SignUpDto
    ): Either<AuthenticationError, UserSignedUp>

    suspend fun signOut(): Either<AuthenticationError, UserSignedOut>

    fun isUserLoggedIn(): Either<AuthenticationError, Boolean>
}

sealed interface Credentials {
    val emailAddress: String
    val password: String

    data class SignInDto(override val emailAddress: String, override val password: String) :
        Credentials

    data class SignUpDto(
        override val emailAddress: String,
        override val password: String,
        val firstName: String,
        val lastName: String,
        val username: String,
        val birthDate: LocalDate,
        val interest: NonEmptySet<Interest>
    ) : Credentials
}

data object UserSignedIn

data object UserSignedUp

data object UserSignedOut

@JvmInline value class AuthenticationError(val message: String)
