package com.example.data.report.implementation

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.raise.either
import arrow.core.raise.zipOrAccumulate
import com.example.data.report.AppName
import com.example.data.report.AppReport
import com.example.data.report.CreateReportDto
import com.example.data.report.DateOfRecording
import com.example.data.report.NotificationsReceived
import com.example.data.report.Report
import com.example.data.report.ReportId
import com.example.data.report.ReportProblem
import com.example.data.report.ScreenTime
import com.example.data.report.TimesOpened
import com.example.data.report.WasOpenedFirst
import com.example.data.user.UserId
import java.util.UUID

internal class ReportImpl(
    override val id: ReportId,
    override val userId: UserId,
    override val dateOfRecording: DateOfRecording,
    override val appReports: List<AppReport>
) : Report {
    override fun totalScreenTime(): Either<ReportProblem, ScreenTime> =
        ScreenTime(appReports.sumOf { it.screenTime.screenTime })

    override fun totalNotificationsReceived(): Either<ReportProblem, NotificationsReceived> =
        NotificationsReceived(appReports.sumOf { it.notificationsReceived.notificationsReceived })

    override fun totalTimesOpened(): Either<ReportProblem, TimesOpened> =
        TimesOpened(appReports.sumOf { it.timesOpened.timesOpened })

    override fun mostUsedApp(): AppName =
        appReports
            .map { Pair(it.appName, it.screenTime.screenTime) }
            .sortedByDescending { it.second }
            .map { it.first }
            .first()
}

fun createReport(createReportDto: CreateReportDto): Either<NonEmptyList<ReportProblem>, Report> =
    either {
        zipOrAccumulate(
            { ReportId(UUID.randomUUID().toString()).bind() },
            { DateOfRecording(createReportDto.dateOfRecording).bind() },
            {
                createReportDto.appReports.map {
                    AppReport(
                        AppName(it.appName).bind(),
                        ScreenTime(it.screenTime).bind(),
                        NotificationsReceived(it.notificationsReceived).bind(),
                        TimesOpened(it.timesOpened).bind(),
                        WasOpenedFirst(it.wasOpenedFirst)
                    )
                }
            }
        ) { reportId, dateOfRecording, appReports ->
            ReportImpl(
                id = reportId,
                userId = createReportDto.userId,
                dateOfRecording = dateOfRecording,
                appReports = appReports
            )
        }
    }
