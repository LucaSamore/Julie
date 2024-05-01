package com.example.data.statistics

import java.time.LocalDate

interface NotificationsDataSource {

    /**
     * Fetch the number of notifications received from every app over the last 24h
     *
     * @return A flow of pairs, where the first element is the app package name and the second
     *   element is the number of notifications the app sent
     */
    suspend fun getPerAppNotificationsReceived(date: LocalDate): List<Pair<String, Int>>
}
