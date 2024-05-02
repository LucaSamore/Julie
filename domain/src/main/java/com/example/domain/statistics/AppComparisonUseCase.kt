package com.example.domain.statistics

import arrow.core.Either
import com.example.data.Problem
import com.example.domain.statistics.implementation.ComparisonReportDto

interface AppComparisonUseCase {
    suspend operator fun invoke(
        appName: String
    ): Either<Problem, Pair<ComparisonReportDto, ComparisonReportDto>>
}
