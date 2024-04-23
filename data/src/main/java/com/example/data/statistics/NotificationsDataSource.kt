package com.example.data.statistics

import kotlinx.coroutines.flow.Flow

interface NotificationsDataSource {

    /**
     * Fetch the number of notifications received from every app over the last 24h
     *
     * @return A flow of pairs, where the first element is the app package name and the second
     *   element is the number of notifications the app sent
     */
    suspend fun getPerAppNotificationsReceived(): Flow<Pair<String, Int>>
}
