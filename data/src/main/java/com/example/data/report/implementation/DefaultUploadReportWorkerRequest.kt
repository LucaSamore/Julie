package com.example.data.report.implementation

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import java.util.concurrent.TimeUnit

object DefaultUploadReportWorkerRequest {
    // val configuration = PeriodicWorkRequestBuilder<UploadReportWorker>(24,
    // TimeUnit.HOURS).build()

    val testConfiguration =
        PeriodicWorkRequestBuilder<UploadReportWorker>(15, TimeUnit.MINUTES)
            .setInitialDelay(10, TimeUnit.SECONDS)
            .build()

    const val NAME = "uploadStats"

    val existingPolicy = ExistingPeriodicWorkPolicy.KEEP
}
