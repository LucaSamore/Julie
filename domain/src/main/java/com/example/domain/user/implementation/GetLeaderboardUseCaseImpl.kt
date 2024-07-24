package com.example.domain.user.implementation

import arrow.core.Either
import com.example.data.Problem
import com.example.data.user.UserProfileRepository
import com.example.domain.user.GetLeaderboardUseCase
import com.example.domain.user.LeaderboardItem
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class GetLeaderboardUseCaseImpl
@Inject
constructor(
    private val userProfileRepository: UserProfileRepository,
    private val ioDispatcher: CoroutineDispatcher
) : GetLeaderboardUseCase {
    override suspend fun invoke(): Either<Problem, List<LeaderboardItem>> =
        withContext(ioDispatcher) {
            userProfileRepository.getLeaderboard().map {
                it.map { u ->
                    LeaderboardItem(
                        username = u.userDetails.username.value,
                        points = u.points.value
                    )
                }
            }
        }
}
