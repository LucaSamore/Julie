package com.example.data

import androidx.work.WorkRequest

interface WorkerManager {
    fun scheduleWorker(workerRequest: WorkRequest)

    fun cancelAllWorkers()
}
