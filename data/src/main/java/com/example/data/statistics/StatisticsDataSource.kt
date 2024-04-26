package com.example.data.statistics

interface StatisticsDataSource {

    fun fetchPerAppScreenTime(): Map<String, Long>

    fun getCurrentScreenTime(): Long

    fun fetchPerAppTimesOpened(): Map<String, Int>

    suspend fun fetchPerAppNotificationsReceived(): Map<String, Int>
}
