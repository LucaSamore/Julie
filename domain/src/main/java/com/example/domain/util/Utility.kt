package com.example.domain.util

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.nonEmptyListOf
import com.example.data.Problem

// TODO: Need to find a better name for this method
internal fun <T> Either<Problem, T>.single(): Either<NonEmptyList<Problem>, T> = mapLeft {
    nonEmptyListOf(it)
}
