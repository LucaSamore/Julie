package com.example.data.report.implementation

import com.example.data.report.AppReportDto
import com.example.data.report.CreateReportDto
import com.example.data.report.Report
import java.time.LocalDate

data class FirestoreReportDto(
    val id: String? = null,
    val userId: String? = null,
    val date: String? = null,
    val totalScreenTime: Long? = null,
    val totalNotificationsReceived: Int? = null,
    val totalTimesOpened: Int? = null,
    val appUsage: List<FirestoreAppUsageDto>? = null
) {
    companion object {
        const val COLLECTION = "reports"

        fun fromEntity(report: Report): FirestoreReportDto =
            FirestoreReportDto(
                id = report.id.reportId,
                userId = report.userId.userId,
                date = report.dateOfRecording.dateOfRecording.toString(),
                totalScreenTime = report.totalScreenTime().screenTime,
                totalNotificationsReceived =
                    report.totalNotificationsReceived().notificationsReceived,
                totalTimesOpened = report.totalTimesOpened().timesOpened,
                appUsage =
                    report.appReports.map {
                        FirestoreAppUsageDto(
                            appName = it.appName.appName,
                            appPackageName = it.appPackageName.appPackageName,
                            screenTime = it.screenTime.screenTime,
                            notifications = it.notificationsReceived.notificationsReceived,
                            timesOpened = it.timesOpened.timesOpened,
                            wasOpenedFirst = it.wasOpenedFirst.wasOpenedFirst
                        )
                    }
            )

        fun toEntity(dto: FirestoreReportDto): Report? =
            createReport(
                    createReportDto =
                        CreateReportDto(
                            userId = dto.userId ?: "",
                            dateOfRecording =
                                LocalDate.parse(dto.date) ?: LocalDate.now().plusDays(1),
                            appReports =
                                dto.appUsage?.map {
                                    AppReportDto(
                                        appName = it.appName ?: "",
                                        appPackageName = it.appPackageName ?: "",
                                        screenTime = it.screenTime ?: 0L,
                                        notificationsReceived = it.notifications ?: 0,
                                        timesOpened = it.timesOpened ?: 0,
                                        wasOpenedFirst = it.wasOpenedFirst ?: false
                                    )
                                } ?: emptyList()
                        )
                )
                .fold({ null }, { it })
    }
}

data class FirestoreAppUsageDto(
    val appName: String? = null,
    val appPackageName: String? = null,
    val screenTime: Long? = null,
    val notifications: Int? = null,
    val timesOpened: Int? = null,
    val wasOpenedFirst: Boolean? = null
)
