package com.example.data.gamification.implementation

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import java.util.concurrent.TimeUnit

object DefaultDailyChallengeWorkerRequest {
    val testConfiguration =
        PeriodicWorkRequestBuilder<DailyChallengeWorker>(30, TimeUnit.MINUTES).build()

    const val NAME = "dailyChallenge"

    val existingPolicy = ExistingPeriodicWorkPolicy.KEEP
}
