package com.example.data

import arrow.core.Either

interface WorkerManager {

    suspend fun scheduleUploadReportWorker(): Either<Problem, Unit>

    suspend fun scheduleDailyChallengeWorker(): Either<Problem, Unit>

    fun cancelAllWorkers()
}
