package com.example.data.report

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import com.example.data.Entity
import com.example.data.Identifier
import com.example.data.Problem
import com.example.data.gamification.today
import com.example.data.user.UserId
import java.time.LocalDate

interface Report : Entity<ReportId> {
    val userId: UserId

    val dateOfRecording: DateOfRecording

    val appReports: List<AppReport>

    fun totalScreenTime(): Either<ReportProblem, ScreenTime>

    fun totalNotificationsReceived(): Either<ReportProblem, NotificationsReceived>

    fun totalTimesOpened(): Either<ReportProblem, TimesOpened>

    fun mostUsedApp(): AppName
}

@JvmInline
value class ReportId private constructor(val reportId: String) : Identifier {
    companion object {
        operator fun invoke(reportId: String): Either<ReportProblem, ReportId> = either {
            ensure(reportId.isNotEmpty()) { ReportIdProblem("Report id cannot be empty") }

            ReportId(reportId)
        }
    }
}

@JvmInline
value class DateOfRecording private constructor(val dateOfRecording: LocalDate) {
    companion object {
        operator fun invoke(dateOfRecording: LocalDate): Either<ReportProblem, DateOfRecording> =
            either {
                ensure(dateOfRecording.isBefore(today().plusDays(1))) {
                    DateOfRecordingProblem("Date of recording cannot be in the future")
                }

                DateOfRecording(dateOfRecording)
            }
    }
}

sealed interface ReportProblem : Problem

@JvmInline value class ReportIdProblem(override val message: String) : ReportProblem

@JvmInline value class DateOfRecordingProblem(override val message: String) : ReportProblem

@JvmInline value class AppNameProblem(override val message: String) : ReportProblem

@JvmInline value class ScreenTimeProblem(override val message: String) : ReportProblem

@JvmInline value class NotificationsReceivedProblem(override val message: String) : ReportProblem

@JvmInline value class TimesOpenedProblem(override val message: String) : ReportProblem
