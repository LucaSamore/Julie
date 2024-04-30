package com.example.data.gamification.implementation

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.data.gamification.DailyChallengeService
import com.example.data.user.implementation.UserDatastore
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@HiltWorker
internal class DailyChallengeWorker
@AssistedInject
constructor(
    private val dailyChallengeService: DailyChallengeService,
    private val userDatastore: UserDatastore,
    @Assisted appContext: Context,
    @Assisted workerParameters: WorkerParameters
) : CoroutineWorker(appContext, workerParameters) {

    override suspend fun doWork(): Result {
        val dateTimeOfRecordingAsString = inputData.getString("dateTime") ?: return Result.failure()
        val dateTime =
            LocalDateTime.parse(
                dateTimeOfRecordingAsString,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            )

        return dailyChallengeService(dateTime).fold({
            Log.e(TAG, it.message)
            Result.retry()
        }) {
            userDatastore.saveDateTimeOfRecordingToDataStore(dateTime.plusDays(1))
            Result.success()
        }
    }

    companion object {
        private const val TAG = "DailyChallengeWorker"
    }
}
