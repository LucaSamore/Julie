package com.example.data.scheduling

import arrow.core.Either
import com.example.data.Problem

interface WorkerManager {

    suspend fun scheduleUploadReportWorker(): Either<Problem, Unit>

    suspend fun scheduleDailyChallengeWorker(): Either<Problem, Unit>

    fun cancelAllWorkers()
}
