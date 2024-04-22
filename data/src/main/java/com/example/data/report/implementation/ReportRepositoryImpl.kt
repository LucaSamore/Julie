package com.example.data.report.implementation

import arrow.core.Either
import com.example.data.RepositoryProblem
import com.example.data.report.Report
import com.example.data.report.ReportId
import com.example.data.report.ReportRepository

internal class ReportRepositoryImpl : ReportRepository {
    override suspend fun create(entity: Report): Either<RepositoryProblem, Report> {
        TODO("Not yet implemented")
    }

    override suspend fun findMany(): Either<RepositoryProblem, Iterable<Report>> {
        TODO("Not yet implemented")
    }

    override suspend fun findOne(id: ReportId): Either<RepositoryProblem, Report> {
        TODO("Not yet implemented")
    }

    override suspend fun update(entity: Report): Either<RepositoryProblem, Report> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: ReportId): Either<RepositoryProblem, Report> {
        TODO("Not yet implemented")
    }
}
