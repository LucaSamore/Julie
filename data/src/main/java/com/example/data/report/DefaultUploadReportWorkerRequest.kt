package com.example.data.report

import androidx.work.PeriodicWorkRequestBuilder
import java.util.concurrent.TimeUnit

object DefaultUploadReportWorkerRequest {
    val configuration = PeriodicWorkRequestBuilder<UploadReportWorker>(24, TimeUnit.HOURS).build()
}
