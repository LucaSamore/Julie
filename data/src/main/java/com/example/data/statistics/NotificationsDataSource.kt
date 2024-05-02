package com.example.data.statistics

import java.time.LocalDate

interface NotificationsDataSource {

    /**
     * Fetch the number of notifications received from every app over the last 24h
     *
     * @param date the date for which to fetch notifications
     * @return A map, where the key element is the app package name and the value element is the
     *   number of notifications the app sent
     */
    suspend fun getPerAppNotificationsReceived(date: LocalDate): Map<String, Int>
}
