package com.example.data.authentication

import arrow.core.Either
import com.example.data.user.BirthDate
import com.example.data.user.EmailAddress
import com.example.data.user.FirstName
import com.example.data.user.Interest
import com.example.data.user.LastName
import com.example.data.user.Password
import com.example.data.user.UserId
import com.example.data.user.Username

interface AuthenticationService {
    suspend fun signInWithEmailAndPassword(
        signInCredentials: Credentials.SignInDto
    ): Either<AuthenticationError, UserSignedIn>

    suspend fun signUpWithEmailAndPassword(
        signInCredentials: Credentials.SignUpDto
    ): Either<AuthenticationError, UserSignedUp>

    suspend fun signOut(): Either<AuthenticationError, UserSignedOut>

    suspend fun isUserLoggedIn(userId: UserId): Either<AuthenticationError, Boolean>
}

sealed interface Credentials {
    val emailAddress: EmailAddress
    val password: Password

    data class SignInDto(override val emailAddress: EmailAddress, override val password: Password) :
        Credentials

    data class SignUpDto(
        override val emailAddress: EmailAddress,
        override val password: Password,
        val firstName: FirstName,
        val lastName: LastName,
        val username: Username,
        val birthDate: BirthDate,
        val interest: Interest
    ) : Credentials
}

data object UserSignedIn

data object UserSignedUp

data object UserSignedOut

@JvmInline value class AuthenticationError(val message: String)
