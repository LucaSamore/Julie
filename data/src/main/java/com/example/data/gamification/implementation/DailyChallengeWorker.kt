package com.example.data.gamification.implementation

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import arrow.core.Either
import com.example.data.authentication.UnknownError
import com.example.data.gamification.DailyChallengeService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
internal class DailyChallengeWorker @AssistedInject constructor(
    private val dailyChallengeService: DailyChallengeService,
    @Assisted appContext: Context,
    @Assisted workerParameters: WorkerParameters
): CoroutineWorker(appContext, workerParameters) {

    override suspend fun doWork(): Result = when (val result = dailyChallengeService()) {
        is Either.Left -> {
            Log.e(TAG, result.leftOrNull()?.message ?: UnknownError)
            Result.retry()
        }
        else -> Result.success()
    }

    companion object {
        private const val TAG = "DailyChallengeWorker"
    }
}