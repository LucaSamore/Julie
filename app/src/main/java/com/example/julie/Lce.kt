package com.example.julie

import arrow.core.NonEmptyList
import com.example.data.Problem
import com.example.data.authentication.AuthenticationProblem
import com.example.data.authentication.UserSignedIn
import com.example.data.authentication.UserSignedOut
import com.example.data.authentication.UserSignedUp

sealed interface Lce<out E, out A> {
    data object Loading : Lce<Nothing, Nothing>

    data class Content<A>(val value: A) : Lce<Nothing, A>

    data class Failure<E>(val error: E) : Lce<E, Nothing>
}

typealias SignInScreenState = Lce<NonEmptyList<Problem>, UserSignedIn>

typealias SignUpScreenState = Lce<NonEmptyList<Problem>, UserSignedUp>

typealias TestScreenState = Lce<AuthenticationProblem, UserSignedOut>
