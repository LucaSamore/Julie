package com.example.data.report.implementation

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
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
    context: Context
) : UploadReportService {

    private val packageManager = context.packageManager

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
        val installedAppPackageNames = getInstalledAppPackageNames()
        val screenTimes = statisticsDataSource.fetchPerAppScreenTimeForLast24h()
        val notifications = statisticsDataSource.fetchPerAppNotificationsReceived()
        val timesOpened = statisticsDataSource.fetchPerAppTimesOpenedForLast24h()
        return installedAppPackageNames.map {
            AppReportDto(
                appName = getAppNameFromPackageName(it),
                screenTime = screenTimes[it] ?: 0L,
                notificationsReceived = notifications[it] ?: 0,
                timesOpened = timesOpened[it] ?: 0,
                wasOpenedFirst = false
            )
        }
    }

    private fun getInstalledAppPackageNames(): List<String> {
        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        val resolvedInfo =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageManager.queryIntentActivities(
                    mainIntent,
                    PackageManager.ResolveInfoFlags.of(0L)
                )
            } else {
                packageManager.queryIntentActivities(mainIntent, 0)
            }
        return resolvedInfo.map { it.activityInfo.packageName }
    }

    private fun getAppNameFromPackageName(packageName: String): String =
        Either.catch { packageManager.getApplicationInfo(packageName, 0) }
            .fold(
                { packageName },
                { packageManager.getApplicationLabel(it).toString() },
            )
}
