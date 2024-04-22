package com.example.data.report.implementation

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

internal class UploadReportWorker(appContext: Context, workerParameters: WorkerParameters) :
    Worker(appContext, workerParameters) {
    override fun doWork(): Result {
        TODO("Not yet implemented")
    }
}
