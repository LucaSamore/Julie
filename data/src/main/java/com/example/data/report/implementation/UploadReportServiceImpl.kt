package com.example.data.report.implementation

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.nonEmptyListOf
import arrow.core.raise.either
import com.example.data.Problem
import com.example.data.report.AppReportDto
import com.example.data.report.CreateReportDto
import com.example.data.report.Report
import com.example.data.report.ReportRepository
import com.example.data.report.UploadReportService
import com.example.data.statistics.StatisticsDataSource
import com.example.data.user.UserIdProblem
import com.example.data.user.implementation.UserDatastore
import java.time.LocalDate
import javax.inject.Inject

internal class UploadReportServiceImpl
@Inject
constructor(
    private val statisticsDataSource: StatisticsDataSource,
    private val reportRepository: ReportRepository,
    private val userDatastore: UserDatastore,
) : UploadReportService {

    override suspend fun invoke(): Either<NonEmptyList<Problem>, Report> = either {
        val userId = userDatastore.getUserId().mapLeft { nonEmptyListOf(UserIdProblem(it)) }.bind()
        val reportDto =
            CreateReportDto(
                userId = userId,
                dateOfRecording = LocalDate.now(),
                appReports = createAppReports()
            )
        val newReport = createReport(reportDto).bind()
        reportRepository.create(newReport).mapLeft { nonEmptyListOf(it) }.bind()
    }

    private suspend fun createAppReports(): List<AppReportDto> {
        val screenTimes = statisticsDataSource.fetchPerAppScreenTime()
        val notifications = statisticsDataSource.fetchPerAppNotificationsReceived()
        TODO()
    }
}
