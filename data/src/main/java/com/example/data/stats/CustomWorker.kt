package com.example.data.stats

interface CustomWorker<W> {
    val worker: W

    val tag: String

    val type: WorkerType

    val inputData: Map<Any, Any>
}

sealed interface WorkerType {
    data object OneTimeWorker

    data object PeriodicWorker
}
