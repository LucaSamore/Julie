package com.example.domain.report

import android.graphics.drawable.Drawable
import arrow.core.Either
import com.example.data.Problem
import java.time.LocalDate

interface GetUserReportsUseCase {
    suspend operator fun invoke(): Either<Problem, Iterable<ReportDto>>
}

data class ReportDto(val date: LocalDate, val appReports: List<AppDto>)

data class AppDto(
    val name: String,
    val icon: Drawable?,
    val screenTime: Long,
    val notificationsReceived: Int,
    val timesOpened: Int
)
