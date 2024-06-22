package com.example.julie

import arrow.core.NonEmptyList
import com.example.data.Problem
import com.example.data.authentication.AuthenticationProblem
import com.example.data.authentication.PasswordReset
import com.example.data.authentication.UserSignedIn
import com.example.data.authentication.UserSignedOut
import com.example.data.authentication.UserSignedUp
import com.example.data.authentication.VerificationEmailSent
import com.example.julie.home.HomeScreenContent
import com.example.julie.smartphoneusage.SmartphoneUsageScreenContent
import com.example.julie.smartphoneusage.StoryContent

internal sealed interface Lce<out E, out A> {
    data object Loading : Lce<Nothing, Nothing>

    data class Content<A>(val value: A) : Lce<Nothing, A>

    data class Failure<E>(val error: E) : Lce<E, Nothing>
}

internal typealias SignInScreenState = Lce<NonEmptyList<Problem>, UserSignedIn>

internal typealias SignUpScreenState = Lce<NonEmptyList<Problem>, UserSignedUp>

internal typealias PasswordResetState = Lce<Problem, PasswordReset>

internal typealias VerifyEmailState = Lce<Problem, VerificationEmailSent>

internal typealias HomeScreenState = Lce<Problem, HomeScreenContent>

internal typealias SmartphoneUsageScreenState = Lce<Problem, SmartphoneUsageScreenContent>

internal typealias StoryScreenState = Lce<Problem, StoryContent>

internal typealias SettingsScreenState = Lce<AuthenticationProblem, UserSignedOut>
