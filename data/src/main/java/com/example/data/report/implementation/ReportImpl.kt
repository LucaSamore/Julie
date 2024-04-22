package com.example.data.report.implementation

import com.example.data.report.AppName
import com.example.data.report.AppReport
import com.example.data.report.DateOfRecording
import com.example.data.report.NotificationsReceived
import com.example.data.report.Report
import com.example.data.report.ReportId
import com.example.data.report.ScreenTime
import com.example.data.report.TimesOpened
import com.example.data.user.UserId

internal class ReportImpl(
    override val id: ReportId,
    override val userId: UserId,
    override val dateOfRecording: DateOfRecording,
    override val appReports: List<AppReport>
) : Report {
    override fun totalScreenTime(): ScreenTime {
        TODO("Not yet implemented")
    }

    override fun totalNotificationsReceived(): NotificationsReceived {
        TODO("Not yet implemented")
    }

    override fun totalTimesOpened(): TimesOpened {
        TODO("Not yet implemented")
    }

    override fun mostUsedApp(): AppName {
        TODO("Not yet implemented")
    }
}
