package com.example.data

import arrow.core.Either

interface Repository<E, I : Identifier> where E : Entity<I> {
    suspend fun create(entity: E): Either<RepositoryProblem, E>

    suspend fun findMany(): Either<RepositoryProblem, Iterable<E>>

    suspend fun findOne(id: I): Either<RepositoryProblem, E>

    suspend fun update(entity: E): Either<RepositoryProblem, E>

    suspend fun delete(id: I): Either<RepositoryProblem, E>
}

@JvmInline value class RepositoryProblem(override val message: String) : Problem
