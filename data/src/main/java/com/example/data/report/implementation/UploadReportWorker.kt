package com.example.data.report.implementation

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.data.report.UploadReportService
import com.example.data.user.implementation.UserDatastore
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@HiltWorker
internal class UploadReportWorker
@AssistedInject
constructor(
    private val uploadReportService: UploadReportService,
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

        return uploadReportService(dateTime).fold({
            it.all.forEach { error -> Log.e(TAG, error.message) }
            Result.retry()
        }) {
            userDatastore.saveDateTimeOfRecordingToDataStore(dateTime.plusDays(1))
            Result.success()
        }
    }

    companion object {
        private const val TAG = "UploadReportWorker"
    }
}
