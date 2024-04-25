package com.example.data.di

import android.content.Context
import com.example.data.statistics.NotificationDao
import com.example.data.statistics.NotificationsDataSource
import com.example.data.statistics.StatisticsDataSource
import com.example.data.statistics.implementation.NotificationsDataSourceImpl
import com.example.data.statistics.implementation.StatisticsDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideStatisticsDataSource(
        notificationsDataSource: NotificationsDataSource,
        @ApplicationContext context: Context
    ): StatisticsDataSource = StatisticsDataSourceImpl(notificationsDataSource, context)

    @Singleton
    @Provides
    fun provideNotificationsDataSource(notificationDao: NotificationDao): NotificationsDataSource =
        NotificationsDataSourceImpl(notificationDao)
}
