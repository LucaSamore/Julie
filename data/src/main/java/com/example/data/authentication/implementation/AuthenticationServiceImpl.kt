package com.example.data.authentication.implementation

import arrow.core.Either
import com.example.data.authentication.AuthenticationError
import com.example.data.authentication.AuthenticationService
import com.example.data.authentication.Credentials
import com.example.data.authentication.UserSignedIn
import com.example.data.authentication.UserSignedOut
import com.example.data.authentication.UserSignedUp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

internal class AuthenticationServiceImpl
@Inject
constructor(private val ioDispatcher: CoroutineDispatcher) : AuthenticationService {

    private val auth = Firebase.auth

    override suspend fun signInWithEmailAndPassword(
        signInCredentials: Credentials.SignInDto
    ): Either<AuthenticationError, UserSignedIn> =
        withContext(ioDispatcher) {
            Either.catch {
                    auth
                        .signInWithEmailAndPassword(
                            signInCredentials.emailAddress,
                            signInCredentials.password
                        )
                        .await()
                }
                .mapLeft { AuthenticationError(it.message ?: UnknownError) }
                .map { UserSignedIn }
        }

    override suspend fun signUpWithEmailAndPassword(
        signInCredentials: Credentials.SignUpDto
    ): Either<AuthenticationError, UserSignedUp> {
        TODO("Not yet implemented")
    }

    override suspend fun signOut(): Either<AuthenticationError, UserSignedOut> =
        withContext(ioDispatcher) {
            Either.catch { auth.signOut() }
                .mapLeft { AuthenticationError(it.message ?: UnknownError) }
                .map { UserSignedOut }
        }

    override fun isUserLoggedIn(): Boolean = auth.currentUser != null
}

internal const val UnknownError = "Unknown error"
