package com.example.data.report

import arrow.core.Either
import com.example.data.Repository
import com.example.data.RepositoryProblem
import com.example.data.user.UserId

interface ReportRepository : Repository<Report, ReportId> {
    suspend fun getFavouriteApps(
        userId: UserId,
        timeSpanInDays: Int,
        top: Int
    ): Either<RepositoryProblem, Map<AppPackageName, Long>>
}
