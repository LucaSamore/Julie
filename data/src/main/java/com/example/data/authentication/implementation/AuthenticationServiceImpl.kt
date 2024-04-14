package com.example.data.authentication.implementation

import arrow.core.Either
import com.example.data.authentication.AuthenticationError
import com.example.data.authentication.AuthenticationService
import com.example.data.authentication.UserSignedIn
import com.example.data.authentication.UserSignedOut
import com.example.data.authentication.UserSignedUp
import com.example.data.authentication.ValidatedCredentials
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await

internal class AuthenticationServiceImpl
@Inject
constructor(private val ioDispatcher: CoroutineDispatcher) : AuthenticationService {

    private val auth = Firebase.auth

    override suspend fun signInWithEmailAndPassword(
        signInCredentials: ValidatedCredentials.SignInDto
    ): Either<AuthenticationError, UserSignedIn> =
        Either.catch {
                auth
                    .signInWithEmailAndPassword(
                        signInCredentials.emailAddress.emailAddress,
                        signInCredentials.password.password
                    )
                    .await()
            }
            .mapLeft { AuthenticationError.fromThrowable(it) }
            .map { UserSignedIn }

    override suspend fun signUpWithEmailAndPassword(
        signUpCredentials: ValidatedCredentials.SignUpDto
    ): Either<AuthenticationError, UserSignedUp> =
        Either.catch {
                auth
                    .createUserWithEmailAndPassword(
                        signUpCredentials.emailAddress.emailAddress,
                        signUpCredentials.password.password
                    )
                    .await()
            }
            .mapLeft { AuthenticationError.fromThrowable(it) }
            .map { UserSignedUp }

    override suspend fun signOut(): Either<AuthenticationError, UserSignedOut> =
        Either.catch { auth.signOut() }
            .mapLeft { AuthenticationError.fromThrowable(it) }
            .map { UserSignedOut }

    override fun isUserLoggedIn(): Boolean = auth.currentUser != null
}
