package com.example.data.statistics.implementation

import com.example.data.statistics.NotificationDao
import com.example.data.statistics.NotificationsDataSource
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class NotificationsDataSourceImpl
@Inject
constructor(private val notificationDao: NotificationDao) : NotificationsDataSource {

    override suspend fun getPerAppNotificationsReceived(): List<Pair<String, Int>> =
        notificationDao.getByDay(LocalDate.now().toString()).map { Pair(it.packageName, it.count) }
}
