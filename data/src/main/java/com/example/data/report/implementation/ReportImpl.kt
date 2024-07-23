package com.example.data.report.implementation

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.raise.either
import arrow.core.raise.zipOrAccumulate
import com.example.data.Problem
import com.example.data.report.AppName
import com.example.data.report.AppPackageName
import com.example.data.report.AppReport
import com.example.data.report.CreateReportDto
import com.example.data.report.DateOfRecording
import com.example.data.report.NotificationsReceived
import com.example.data.report.Report
import com.example.data.report.ReportId
import com.example.data.report.ScreenTime
import com.example.data.report.TimesOpened
import com.example.data.report.WasOpenedFirst
import com.example.data.user.UserId
import java.util.UUID

internal data class ReportImpl(
    override val id: ReportId,
    override val userId: UserId,
    override val dateOfRecording: DateOfRecording,
    override val appReports: List<AppReport>
) : Report {
    override val totalScreenTime: ScreenTime
        get() {
            val total = appReports.sumOf { it.screenTime.value }
            return ScreenTime(total).fold({ ScreenTime.default }) { it }
        }

    override val totalNotificationsReceived: NotificationsReceived
        get() {
            val total = appReports.sumOf { it.notificationsReceived.value }
            return NotificationsReceived(total).fold({ NotificationsReceived.default }) { it }
        }

    override val totalTimesOpened: TimesOpened
        get() {
            val total = appReports.sumOf { it.timesOpened.value }
            return TimesOpened(total).fold({ TimesOpened.default }) { it }
        }

    override val mostUsedApp: AppName
        get() =
            appReports
                .map { it.appName to it.screenTime.value }
                .sortedByDescending { it.second }
                .map { it.first }
                .first()
}

fun createReport(createReportDto: CreateReportDto): Either<NonEmptyList<Problem>, Report> = either {
    zipOrAccumulate(
        { ReportId(UUID.randomUUID().toString()).bind() },
        { UserId(createReportDto.userId).bind() },
        { DateOfRecording(createReportDto.dateOfRecording).bind() },
        {
            createReportDto.appReports.map {
                AppReport(
                    AppName(it.appName).bind(),
                    AppPackageName(it.appPackageName).bind(),
                    ScreenTime(it.screenTime).bind(),
                    NotificationsReceived(it.notificationsReceived).bind(),
                    TimesOpened(it.timesOpened).bind(),
                    WasOpenedFirst(it.wasOpenedFirst)
                )
            }
        }
    ) { reportId, userId, dateOfRecording, appReports ->
        ReportImpl(
            id = reportId,
            userId = userId,
            dateOfRecording = dateOfRecording,
            appReports = appReports
        )
    }
}
