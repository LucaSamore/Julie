package com.example.data.statistics

interface StatisticsDataSource {

    fun fetchPerAppScreenTime(): Map<String, Long>

    suspend fun fetchPerAppNotificationsReceived(): Map<String, Int>
}
