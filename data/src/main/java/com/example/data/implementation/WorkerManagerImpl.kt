package com.example.data.implementation

import android.content.Context
import androidx.work.WorkManager
import com.example.data.WorkerManager
import com.example.data.gamification.implementation.DefaultDailyChallengeWorkerRequest
import com.example.data.report.implementation.DefaultUploadReportWorkerRequest
import javax.inject.Inject

internal class WorkerManagerImpl @Inject constructor(context: Context) : WorkerManager {

    private val workManager = WorkManager.getInstance(context)

    override fun scheduleUploadReportWorker() {
        workManager.enqueueUniquePeriodicWork(
            DefaultUploadReportWorkerRequest.NAME,
            DefaultUploadReportWorkerRequest.existingPolicy,
            DefaultUploadReportWorkerRequest.testConfiguration
        )
    }

    override fun scheduleDailyChallengeWorker() {
        workManager.enqueueUniquePeriodicWork(
            DefaultDailyChallengeWorkerRequest.NAME,
            DefaultDailyChallengeWorkerRequest.existingPolicy,
            DefaultDailyChallengeWorkerRequest.testConfiguration
        )
    }

    override fun cancelAllWorkers() {
        workManager.cancelAllWork()
    }
}
