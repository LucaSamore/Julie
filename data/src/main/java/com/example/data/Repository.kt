package com.example.data

import arrow.core.Either

internal interface Repository<E, I : Identifier> where E : Entity<I> {
    suspend fun create(entity: E): Either<Nothing, Nothing>

    suspend fun findMany(): Either<Nothing, Sequence<E>>

    suspend fun findOne(id: I): Either<Nothing, E>

    suspend fun update(entity: E): Either<Nothing, Nothing>

    suspend fun delete(id: I): Either<Nothing, Nothing>
}
