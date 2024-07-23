package com.example.domain.report.implementation

import com.example.data.scheduling.WorkerManager
import com.example.domain.report.ScheduleUploadReportWorkerUseCase
import javax.inject.Inject

internal class ScheduleUploadReportWorkerUseCaseImpl
@Inject
constructor(private val workerManager: WorkerManager) : ScheduleUploadReportWorkerUseCase {
    override suspend fun invoke() {
        workerManager.scheduleUploadReportWorker()
    }
}
