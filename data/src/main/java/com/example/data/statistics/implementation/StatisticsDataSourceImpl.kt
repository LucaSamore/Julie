package com.example.data.statistics.implementation

import com.example.data.statistics.NotificationsDataSource
import com.example.data.statistics.ScreenTimeDataSource
import com.example.data.statistics.StatisticsDataSource
import javax.inject.Inject
import kotlinx.coroutines.flow.toList

internal class StatisticsDataSourceImpl
@Inject
constructor(
    private val screenTimeDataSource: ScreenTimeDataSource,
    private val notificationsDataSource: NotificationsDataSource,
) : StatisticsDataSource {

    override fun fetchPerAppScreenTime(): Map<String, Long> =
        screenTimeDataSource.getPerAppScreenTime()

    override suspend fun fetchPerAppNotificationsReceived(): Map<String, Int> =
        notificationsDataSource.getPerAppNotificationsReceived().toList().toMap()
}
