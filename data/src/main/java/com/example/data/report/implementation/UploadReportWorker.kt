package com.example.data.report.implementation

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.data.report.ReportRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@HiltWorker
internal class UploadReportWorker
@AssistedInject
constructor(
    @Assisted private val reportRepository: ReportRepository,
    @Assisted appContext: Context,
    @Assisted workerParameters: WorkerParameters
) : Worker(appContext, workerParameters) {
    override fun doWork(): Result {
        val currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
        Log.i("output", "Hello @ time: $currentTime")
        return Result.success()
    }
}
