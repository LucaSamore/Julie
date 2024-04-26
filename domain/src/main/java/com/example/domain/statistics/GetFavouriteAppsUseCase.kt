package com.example.domain.statistics

import arrow.core.Either
import com.example.data.Problem
import com.example.data.report.AppName

interface GetFavouriteAppsUseCase {
    suspend operator fun invoke(): Either<Problem, List<AppName>>
}
