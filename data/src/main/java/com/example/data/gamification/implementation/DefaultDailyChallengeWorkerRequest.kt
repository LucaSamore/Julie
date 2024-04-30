package com.example.data.gamification.implementation

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import java.util.concurrent.TimeUnit

object DefaultDailyChallengeWorkerRequest {
    val testConfiguration = PeriodicWorkRequestBuilder<DailyChallengeWorker>(24, TimeUnit.HOURS)

    const val NAME = "dailyChallenge"

    val existingPolicy = ExistingPeriodicWorkPolicy.KEEP
}
