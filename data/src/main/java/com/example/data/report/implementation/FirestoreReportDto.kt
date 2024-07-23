package com.example.data.report.implementation

import android.util.Log
import com.example.data.report.AppReportDto
import com.example.data.report.CreateReportDto
import com.example.data.report.Report
import java.time.LocalDate

data class FirestoreReportDto(
    val id: String? = null,
    val userId: String? = null,
    val date: String? = null,
    val appUsage: List<FirestoreAppUsageDto>? = null
) {
    companion object {
        const val COLLECTION = "reports"

        private const val TAG = "FirestoreReportDto"

        fun fromEntity(report: Report): FirestoreReportDto {
            return FirestoreReportDto(
                id = report.id.value,
                userId = report.userId.value,
                date = report.dateOfRecording.value.toString(),
                appUsage =
                    report.appReports.map {
                        FirestoreAppUsageDto(
                            appName = it.appName.value,
                            appPackageName = it.appPackageName.value,
                            screenTime = it.screenTime.value,
                            notifications = it.notificationsReceived.value,
                            timesOpened = it.timesOpened.value,
                            wasOpenedFirst = it.wasOpenedFirst.value
                        )
                    }
            )
        }

        fun toEntity(dto: FirestoreReportDto): Report? {
            val reportDto =
                CreateReportDto(
                    userId = dto.userId ?: "",
                    dateOfRecording = LocalDate.parse(dto.date) ?: LocalDate.now().plusDays(1),
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

            return createReport(reportDto).fold({
                it.forEach { e -> Log.e(TAG, e.message) }
                null
            }) {
                it
            }
        }
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
