package com.example.data.report.implementation

import com.example.data.report.Report

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
                            screenTime = it.screenTime.screenTime,
                            notifications = it.notificationsReceived.notificationsReceived,
                            timesOpened = it.timesOpened.timesOpened,
                            wasOpenedFirst = it.wasOpenedFirst.wasOpenedFirst
                        )
                    }
            )
    }
}

data class FirestoreAppUsageDto(
    val appName: String? = null,
    val screenTime: Long? = null,
    val notifications: Int? = null,
    val timesOpened: Int? = null,
    val wasOpenedFirst: Boolean? = null
)
