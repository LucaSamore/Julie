package com.example.domain.util

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.nonEmptyListOf
import com.example.data.Problem

internal fun <T> Either<Problem, T>.single(): Either<NonEmptyList<Problem>, T> = mapLeft {
    nonEmptyListOf(it)
}
