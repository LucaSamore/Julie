package com.example.data.report.implementation

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

internal class UploadReportWorker(appContext: Context, workerParameters: WorkerParameters) :
    Worker(appContext, workerParameters) {
    override fun doWork(): Result {
        val currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
        Log.i("output", "Hello @ time: $currentTime")
        return Result.success()
    }
}
