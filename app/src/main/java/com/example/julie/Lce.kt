package com.example.julie

import com.example.data.authentication.AuthenticationError
import com.example.data.authentication.UserSignedIn
import com.example.data.authentication.UserSignedOut

sealed interface Lce<out E, out A> {
    data object Loading : Lce<Nothing, Nothing>

    data class Content<A>(val value: A) : Lce<Nothing, A>

    data class Failure<E>(val error: E) : Lce<E, Nothing>
}

typealias SignInScreenState = Lce<AuthenticationError, UserSignedIn>

typealias TestScreenState = Lce<AuthenticationError, UserSignedOut>
