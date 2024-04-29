package com.example.data

interface WorkerManager {

    fun scheduleUploadReportWorker()

    suspend fun scheduleDailyChallengeWorker()

    fun cancelAllWorkers()
}
