package com.example.data

interface WorkerManager {

    fun scheduleUploadReportWorker()

    fun scheduleDailyChallengeWorker()

    fun cancelAllWorkers()
}
