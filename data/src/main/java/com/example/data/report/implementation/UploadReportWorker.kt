package com.example.data.report.implementation

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import arrow.core.Either
import com.example.data.report.UploadReportService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDateTime

@HiltWorker
internal class UploadReportWorker
@AssistedInject
constructor(
    private val uploadReportService: UploadReportService,
    @Assisted appContext: Context,
    @Assisted workerParameters: WorkerParameters
) : CoroutineWorker(appContext, workerParameters) {
    override suspend fun doWork(): Result =
        when (val result = uploadReportService()) {
            is Either.Left -> {
                result.leftOrNull()?.all?.forEach {
                    Log.e(TAG, "${it.message} @ ${LocalDateTime.now()}")
                }
                Result.retry()
            }
            else -> Result.success()
        }

    companion object {
        private const val TAG = "UploadReportWorker"
    }
}
