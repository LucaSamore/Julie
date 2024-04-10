package com.example.julie

sealed interface Lce<out E, out A> {
    data object Loading : Lce<Nothing, Nothing>

    data class Content<A>(val value: A) : Lce<Nothing, A>

    data class Failure<E>(val error: E) : Lce<E, Nothing>
}
