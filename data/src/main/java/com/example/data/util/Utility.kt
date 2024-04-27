package com.example.data.util

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.nonEmptyListOf
import com.example.data.Problem

fun <T> Either<Problem, T>.accumulateIfLeft(): Either<NonEmptyList<Problem>, T> = mapLeft {
    nonEmptyListOf(it)
}
