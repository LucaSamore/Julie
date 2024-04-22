package com.example.data.report

import com.example.data.user.UserId
import java.time.LocalDate

data class CreateReportDto(
    val userId: UserId,
    val dateOfRecording: LocalDate = LocalDate.now(),
    val appReports: List<AppReportDto>
)

data class AppReportDto(
    val appName: String,
    val screenTime: Int,
    val notificationsReceived: Int,
    val timesOpened: Int,
    val wasOpenedFirst: Boolean
)
