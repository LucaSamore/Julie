package com.example.data.report

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure

data class AppReport(
    val appName: AppName,
    val appPackageName: AppPackageName,
    val screenTime: ScreenTime,
    val notificationsReceived: NotificationsReceived,
    val timesOpened: TimesOpened,
    val wasOpenedFirst: WasOpenedFirst
)

@JvmInline
value class AppName private constructor(val value: String) {
    companion object {
        operator fun invoke(appName: String): Either<ReportProblem, AppName> = either {
            ensure(appName.isNotBlank()) { AppNameProblem("App name cannot be empty") }

            AppName(appName)
        }
    }
}

@JvmInline
value class AppPackageName private constructor(val value: String) {
    companion object {
        operator fun invoke(appPackageName: String): Either<ReportProblem, AppPackageName> =
            either {
                ensure(appPackageName.isNotBlank()) {
                    AppPackageNameProblem("App package name cannot be empty")
                }

                AppPackageName(appPackageName)
            }
    }
}

@JvmInline
value class ScreenTime private constructor(val value: Long) {
    companion object {
        val default
            get() = ScreenTime(0L)

        operator fun invoke(screenTime: Long): Either<ReportProblem, ScreenTime> = either {
            ensure(screenTime >= 0) { ScreenTimeProblem("Screen time cannot be negative") }

            ScreenTime(screenTime)
        }
    }

    operator fun plus(other: ScreenTime): ScreenTime = ScreenTime(value + other.value)
}

@JvmInline
value class NotificationsReceived private constructor(val value: Int) {
    companion object {
        val default
            get() = NotificationsReceived(0)

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
value class TimesOpened private constructor(val value: Int) {
    companion object {
        val default
            get() = TimesOpened(0)

        operator fun invoke(timesOpened: Int): Either<ReportProblem, TimesOpened> = either {
            ensure(timesOpened >= 0) { TimesOpenedProblem("Times opened cannot be negative") }

            TimesOpened(timesOpened)
        }
    }
}

@JvmInline value class WasOpenedFirst(val value: Boolean)
