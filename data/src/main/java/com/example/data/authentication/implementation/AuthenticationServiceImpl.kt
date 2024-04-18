package com.example.data.authentication.implementation

import arrow.core.Either
import com.example.data.authentication.AuthenticationError
import com.example.data.authentication.AuthenticationProblem
import com.example.data.authentication.AuthenticationService
import com.example.data.authentication.PasswordReset
import com.example.data.authentication.UserSignedIn
import com.example.data.authentication.UserSignedOut
import com.example.data.authentication.UserSignedUp
import com.example.data.authentication.ValidatedCredentials
import com.example.data.authentication.VerificationEmailSent
import com.example.data.user.EmailAddress
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

internal class AuthenticationServiceImpl : AuthenticationService {

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

    override suspend fun passwordReset(
        emailAddress: EmailAddress
    ): Either<AuthenticationProblem, PasswordReset> =
        Either.catch { auth.sendPasswordResetEmail(emailAddress.emailAddress).await() }
            .mapLeft { AuthenticationError.fromThrowable(it) }
            .map { PasswordReset }

    override suspend fun signOut(): Either<AuthenticationError, UserSignedOut> =
        Either.catch { auth.signOut() }
            .mapLeft { AuthenticationError.fromThrowable(it) }
            .map { UserSignedOut }

    override suspend fun sendVerificationEmail():
        Either<AuthenticationError, VerificationEmailSent> =
        Either.catch { auth.currentUser!!.sendEmailVerification().await() }
            .mapLeft { AuthenticationError.fromThrowable(it) }
            .map { VerificationEmailSent }

    override fun isEmailVerified(): Boolean = auth.currentUser!!.isEmailVerified

    override fun isUserLoggedIn(): Boolean = auth.currentUser != null
}
