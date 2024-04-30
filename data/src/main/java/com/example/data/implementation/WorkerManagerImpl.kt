package com.example.data.implementation

import android.content.Context
import androidx.work.WorkManager
import androidx.work.workDataOf
import arrow.core.Either
import arrow.core.raise.either
import com.example.data.Problem
import com.example.data.WorkerManager
import com.example.data.gamification.implementation.DefaultDailyChallengeWorkerRequest
import com.example.data.report.implementation.DefaultUploadReportWorkerRequest
import com.example.data.user.implementation.UserDatastore
import javax.inject.Inject

internal class WorkerManagerImpl
@Inject
constructor(private val userDatastore: UserDatastore, context: Context) : WorkerManager {

    private val workManager = WorkManager.getInstance(context)

    override suspend fun scheduleUploadReportWorker(): Either<Problem, Unit> = either {
        val dateTime = userDatastore.getDateTimeOfRecording().bind()
        val workerRequest =
            DefaultUploadReportWorkerRequest.testConfiguration
                .setInputData(workDataOf("dateTime" to dateTime))
                .build()
        workManager.enqueueUniquePeriodicWork(
            DefaultUploadReportWorkerRequest.NAME,
            DefaultUploadReportWorkerRequest.existingPolicy,
            workerRequest
        )
    }

    override suspend fun scheduleDailyChallengeWorker(): Either<Problem, Unit> = either {
        val dateTime = userDatastore.getDateTimeOfRecording().bind()
        val workerRequest =
            DefaultDailyChallengeWorkerRequest.testConfiguration
                .setInputData(workDataOf("dateTime" to dateTime))
                .build()
        workManager.enqueueUniquePeriodicWork(
            DefaultDailyChallengeWorkerRequest.NAME,
            DefaultDailyChallengeWorkerRequest.existingPolicy,
            workerRequest
        )
    }

    override fun cancelAllWorkers() {
        workManager.cancelAllWork()
    }
}
