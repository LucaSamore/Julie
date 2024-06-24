package com.example.data.statistics.implementation

import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import com.example.data.statistics.NotificationsDataSource
import com.example.data.statistics.StatisticsDataSource
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import javax.inject.Inject

internal class StatisticsDataSourceImpl
@Inject
constructor(private val notificationsDataSource: NotificationsDataSource, context: Context) :
    StatisticsDataSource {

    private val usageStatsManager =
        context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    override fun fetchPerAppScreenTime(date: LocalDate, endTime: LocalDateTime): Map<String, Long> =
        getDailyStats(date, endTime).associate { it.packageName to it.totalTime }

    override fun getScreenTime(date: LocalDate, endTime: LocalDateTime): Long =
        getDailyStats(date, endTime).sumOf { it.totalTime }

    override fun fetchPerAppTimesOpened(date: LocalDate, endTime: LocalDateTime): Map<String, Int> =
        getDailyStats(date, endTime).associate { it.packageName to it.startTimes.count() }

    override suspend fun fetchPerAppNotificationsReceived(date: LocalDate): Map<String, Int> =
        notificationsDataSource.getPerAppNotificationsReceived(date)

    private fun getDailyStats(
        date: LocalDate = LocalDate.now(),
        endDateTime: LocalDateTime
    ): List<Stat> {
        val utcZone = ZoneId.of("UTC")
        val defaultZone = ZoneId.systemDefault()

        val startDateTime = date.atStartOfDay(defaultZone).withZoneSameInstant(utcZone)
        val startMillis = startDateTime.toInstant().toEpochMilli()
        val endMillis = endDateTime.toInstant(ZoneOffset.UTC).toEpochMilli()

        val sortedEvents = mutableMapOf<String, MutableList<UsageEvents.Event>>()
        val systemEvents = usageStatsManager.queryEvents(startMillis, endMillis)

        while (systemEvents.hasNextEvent()) {
            val event = UsageEvents.Event()
            systemEvents.getNextEvent(event)
            sortedEvents.computeIfAbsent(event.packageName) { mutableListOf() }.add(event)
        }

        return sortedEvents.map { (packageName, events) ->
            val (totalTime, startTimes) =
                calculateUsageStats(events, startMillis, endMillis, utcZone, defaultZone)
            Stat(packageName, totalTime, startTimes)
        }
    }

    private fun calculateUsageStats(
        events: List<UsageEvents.Event>,
        startMillis: Long,
        endMillis: Long,
        utcZone: ZoneId,
        defaultZone: ZoneId
    ): Pair<Long, List<ZonedDateTime>> {
        var totalTime = 0L
        var startTime = 0L
        val startTimes = mutableListOf<ZonedDateTime>()

        events.forEach {
            when (it.eventType) {
                UsageEvents.Event.ACTIVITY_RESUMED -> {
                    startTime = it.timeStamp
                    startTimes.add(
                        Instant.ofEpochMilli(startTime)
                            .atZone(utcZone)
                            .withZoneSameInstant(defaultZone)
                    )
                }
                UsageEvents.Event.ACTIVITY_PAUSED -> {
                    val endTime = it.timeStamp
                    totalTime += calculateSessionTime(startTime, endTime, startMillis)
                    startTime = 0L
                }
            }
        }

        if (startTime != 0L) {
            totalTime += endMillis - 1000 - startTime
        }

        return totalTime to startTimes
    }

    private fun calculateSessionTime(startTime: Long, endTime: Long, defaultStartTime: Long): Long {
        val adjustedStartTime = if (startTime == 0L) defaultStartTime else startTime
        return endTime - adjustedStartTime
    }
}

private data class Stat(
    val packageName: String,
    val totalTime: Long,
    val startTimes: List<ZonedDateTime>
)
