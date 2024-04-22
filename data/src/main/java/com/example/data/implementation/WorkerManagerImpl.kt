package com.example.data.implementation

import android.content.Context
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.data.WorkerManager
import javax.inject.Inject

internal class WorkerManagerImpl @Inject constructor(context: Context) : WorkerManager {

    private val workManager = WorkManager.getInstance(context)

    override fun scheduleWorker(workerRequest: WorkRequest) {
        TODO("Not yet implemented")
    }

    override fun cancelAllWorkers() {
        workManager.cancelAllWork()
    }
}
