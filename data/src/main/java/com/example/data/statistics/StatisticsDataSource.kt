package com.example.data.statistics

import java.time.LocalDate
import java.time.LocalDateTime

interface StatisticsDataSource {

    fun fetchPerAppScreenTime(date: LocalDate, endTime: LocalDateTime): Map<String, Long>

    fun getScreenTime(date: LocalDate, endTime: LocalDateTime): Long

    fun fetchPerAppTimesOpened(date: LocalDate, endTime: LocalDateTime): Map<String, Int>

    suspend fun fetchPerAppNotificationsReceived(): Map<String, Int>
}
