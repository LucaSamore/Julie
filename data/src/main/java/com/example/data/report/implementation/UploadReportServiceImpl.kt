package com.example.data.report.implementation

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.raise.either
import com.example.data.PackageManagerUtils
import com.example.data.Problem
import com.example.data.report.AppReportDto
import com.example.data.report.CreateReportDto
import com.example.data.report.Report
import com.example.data.report.ReportRepository
import com.example.data.report.UploadReportService
import com.example.data.statistics.StatisticsDataSource
import com.example.data.user.implementation.UserDatastore
import com.example.data.util.accumulateIfLeft
import java.time.LocalDate
import javax.inject.Inject

internal class UploadReportServiceImpl
@Inject
constructor(
    private val statisticsDataSource: StatisticsDataSource,
    private val reportRepository: ReportRepository,
    private val userDatastore: UserDatastore,
    private val packageManagerUtils: PackageManagerUtils
) : UploadReportService {

    override suspend fun invoke(): Either<NonEmptyList<Problem>, Report> = either {
        val userId = userDatastore.getUserId().accumulateIfLeft().bind()
        val reportDto =
            CreateReportDto(
                userId = userId.userId,
                dateOfRecording = LocalDate.now(),
                appReports = createAppReports()
            )
        val newReport = createReport(reportDto).bind()
        reportRepository.create(newReport).accumulateIfLeft().bind()
    }

    private suspend fun createAppReports(): List<AppReportDto> {
        val installedAppPackageNames = packageManagerUtils.getInstalledAppPackageNames()
        val screenTimes = statisticsDataSource.fetchPerAppScreenTime()
        val notifications = statisticsDataSource.fetchPerAppNotificationsReceived()
        val timesOpened = statisticsDataSource.fetchPerAppTimesOpened()
        return installedAppPackageNames.map {
            AppReportDto(
                appName = packageManagerUtils.getAppNameFromPackageName(it),
                appPackageName = it,
                screenTime = screenTimes[it] ?: 0L,
                notificationsReceived = notifications[it] ?: 0,
                timesOpened = timesOpened[it] ?: 0,
                wasOpenedFirst = false
            )
        }
    }
}
