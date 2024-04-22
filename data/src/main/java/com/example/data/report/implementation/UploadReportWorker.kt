package com.example.data.report.implementation

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import arrow.core.Either
import com.example.data.report.UploadReportService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@HiltWorker
internal class UploadReportWorker
@AssistedInject
constructor(
    private val uploadReportService: UploadReportService,
    @Assisted appContext: Context,
    @Assisted workerParameters: WorkerParameters
) : CoroutineWorker(appContext, workerParameters) {
    override suspend fun doWork(): Result {
        val currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
        Log.i("output", "Hello @ time: $currentTime")

        return when (uploadReportService()) {
            is Either.Left -> {
                Result.retry()
            }
            else -> Result.success()
        }
    }
}
