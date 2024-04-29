package com.example.data.statistics

interface StatisticsDataSource {

    fun fetchPerAppScreenTime(): Map<String, Long>

    fun getCurrentScreenTime(endTime: Long): Long

    fun fetchPerAppTimesOpened(): Map<String, Int>

    suspend fun fetchPerAppNotificationsReceived(): Map<String, Int>
}
