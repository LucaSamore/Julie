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

    /**
     * Solution provided by @jguerinet
     * https://stackoverflow.com/questions/36238481/android-usagestatsmanager-not-returning-correct-daily-results
     */
    private fun getDailyStats(
        date: LocalDate = LocalDate.now(),
        endDateTime: LocalDateTime
    ): List<Stat> {
        // The timezones we'll need
        val utc = ZoneId.of("UTC")
        val defaultZone = ZoneId.systemDefault()

        // Set the starting and ending times to be midnight in UTC time
        val startDate = date.atStartOfDay(defaultZone).withZoneSameInstant(utc)

        val start = startDate.toInstant().toEpochMilli()
        val end = endDateTime.toInstant(ZoneOffset.UTC).toEpochMilli()

        // This will keep a map of all of the events per package name
        val sortedEvents = mutableMapOf<String, MutableList<UsageEvents.Event>>()

        // Query the list of events that has happened within that time frame
        val systemEvents = usageStatsManager.queryEvents(start, end)

        while (systemEvents.hasNextEvent()) {
            val event = UsageEvents.Event()
            systemEvents.getNextEvent(event)

            // Get the list of events for the package name, create one if it doesn't exist
            val packageEvents = sortedEvents[event.packageName] ?: mutableListOf()
            packageEvents.add(event)
            sortedEvents[event.packageName] = packageEvents
        }

        // This will keep a list of our final stats
        val stats = mutableListOf<Stat>()

        // Go through the events by package name
        sortedEvents.forEach { (packageName, events) ->
            // Keep track of the current start and end times
            var startTime = 0L
            var endTime = 0L
            // Keep track of the total usage time for this app
            var totalTime = 0L
            // Keep track of the start times for this app
            val startTimes = mutableListOf<ZonedDateTime>()
            events.forEach {
                if (it.eventType == UsageEvents.Event.ACTIVITY_RESUMED) {
                    // App was moved to the foreground: set the start time
                    startTime = it.timeStamp
                    // Add the start time within this timezone to the list
                    startTimes.add(
                        Instant.ofEpochMilli(startTime).atZone(utc).withZoneSameInstant(defaultZone)
                    )
                } else if (it.eventType == UsageEvents.Event.ACTIVITY_PAUSED) {
                    // App was moved to background: set the end time
                    endTime = it.timeStamp
                }

                // If there's an end time with no start time, this might mean that
                //  The app was started on the previous day, so take midnight
                //  As the start time
                if (startTime == 0L && endTime != 0L) {
                    startTime = start
                }

                // If both start and end are defined, we have a session
                if (startTime != 0L && endTime != 0L) {
                    // Add the session time to the total time
                    totalTime += endTime - startTime
                    // Reset the start/end times to 0
                    startTime = 0L
                    endTime = 0L
                }
            }

            // If there is a start time without an end time, this might mean that
            //  the app was used past midnight, so take (midnight - 1 second)
            //  as the end time
            if (startTime != 0L && endTime == 0L) {
                totalTime += end - 1000 - startTime
            }
            stats.add(Stat(packageName, totalTime, startTimes))
        }
        return stats
    }
}

private data class Stat(
    val packageName: String,
    val totalTime: Long,
    val startTimes: List<ZonedDateTime>
)
