package com.example.data.report

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure

data class AppReport(
    val appName: AppName,
    val screenTime: ScreenTime,
    val notificationsReceived: NotificationsReceived,
    val timesOpened: TimesOpened,
    val wasOpenedFirst: WasOpenedFirst
)

@JvmInline
value class AppName private constructor(val appName: String) {
    companion object {
        operator fun invoke(appName: String): Either<ReportProblem, AppName> = either {
            ensure(appName.isNotBlank()) { AppNameProblem("App name cannot be empty") }

            AppName(appName)
        }
    }
}

@JvmInline
value class ScreenTime private constructor(val screenTime: Int) {
    companion object {
        operator fun invoke(screenTime: Int): Either<ReportProblem, ScreenTime> = either {
            ensure(screenTime >= 0) { ScreenTimeProblem("Screen time cannot be negative") }

            ScreenTime(screenTime)
        }
    }
}

@JvmInline
value class NotificationsReceived private constructor(val notificationsReceived: Int) {
    companion object {
        operator fun invoke(
            notificationsReceived: Int
        ): Either<ReportProblem, NotificationsReceived> = either {
            ensure(notificationsReceived >= 0) {
                NotificationsReceivedProblem("Notifications received cannot be negative")
            }

            NotificationsReceived(notificationsReceived)
        }
    }
}

@JvmInline
value class TimesOpened private constructor(val timesOpened: Int) {
    companion object {
        operator fun invoke(timesOpened: Int): Either<ReportProblem, TimesOpened> = either {
            ensure(timesOpened >= 0) { TimesOpenedProblem("Times opened cannot be negative") }

            TimesOpened(timesOpened)
        }
    }
}

@JvmInline value class WasOpenedFirst(val wasOpenedFirst: Boolean)
