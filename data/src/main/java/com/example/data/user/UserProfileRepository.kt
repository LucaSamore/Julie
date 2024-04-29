package com.example.data.user

import arrow.core.Either
import com.example.data.Problem
import com.example.data.Repository
import com.example.data.RepositoryProblem
import com.example.data.gamification.Streak

interface UserProfileRepository : Repository<UserProfile, UserId> {

    suspend fun getUserIdByEmailAddress(emailAddress: EmailAddress): Either<Problem, UserId>

    suspend fun isEmailAddressAlreadyInUse(
        emailAddress: EmailAddress
    ): Either<RepositoryProblem, Boolean>

    suspend fun isUsernameAlreadyInUse(username: Username): Either<RepositoryProblem, Boolean>

    suspend fun addEndedStreak(streak: Streak): Either<RepositoryProblem, Streak>

    suspend fun getPastStreaks(userId: UserId): Either<RepositoryProblem, Iterable<Streak>>
}
