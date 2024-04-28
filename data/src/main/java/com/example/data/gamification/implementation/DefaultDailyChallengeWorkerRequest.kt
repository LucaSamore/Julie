package com.example.data.gamification.implementation

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import com.example.data.report.implementation.UploadReportWorker
import java.util.concurrent.TimeUnit

object DefaultDailyChallengeWorkerRequest {
    val testConfiguration =
        PeriodicWorkRequestBuilder<UploadReportWorker>(30, TimeUnit.MINUTES).build()

    const val NAME = "dailyChallenge"

    val existingPolicy = ExistingPeriodicWorkPolicy.KEEP
}