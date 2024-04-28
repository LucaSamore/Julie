package com.example.domain.user.implementation

import arrow.core.Either
import arrow.core.raise.either
import com.example.data.Problem
import com.example.data.user.UserProfileRepository
import com.example.data.user.implementation.UserDatastore
import com.example.domain.user.GetUserProfileGamificationDataUseCase
import com.example.domain.user.UserGamificationDataDto
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class GetUserProfileGamificationDataUseCaseImpl
@Inject
constructor(
    private val userProfileRepository: UserProfileRepository,
    private val userDatastore: UserDatastore,
    private val ioDispatcher: CoroutineDispatcher
) : GetUserProfileGamificationDataUseCase {

    override suspend fun invoke(): Either<Problem, UserGamificationDataDto> =
        withContext(ioDispatcher) {
            either {
                val userId = userDatastore.getUserId().bind()
                val user = userProfileRepository.findOne(userId).bind()
                UserGamificationDataDto(
                    threshold = user.threshold.valueInMillis.valueInMillis,
                    points = user.points.points,
                    currentStreakValue = user.currentStreak.value.value
                )
            }
        }
}
