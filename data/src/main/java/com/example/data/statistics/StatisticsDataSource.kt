package com.example.data.statistics

interface StatisticsDataSource {

    fun fetchPerAppScreenTimeForLast24h(): Map<String, Long>

    fun getCurrentScreenTime(): Long

    fun fetchPerAppTimesOpenedForLast24h(): Map<String, Int>

    suspend fun fetchPerAppNotificationsReceived(): Map<String, Int>
}
