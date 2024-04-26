package com.example.data.report

import java.time.LocalDate

data class CreateReportDto(
    val userId: String,
    val dateOfRecording: LocalDate = LocalDate.now(),
    val appReports: List<AppReportDto>
)

data class AppReportDto(
    val appName: String,
    val appPackageName: String,
    val screenTime: Long,
    val notificationsReceived: Int,
    val timesOpened: Int,
    val wasOpenedFirst: Boolean
)
