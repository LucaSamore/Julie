package com.example.data.di

import android.content.Context
import com.example.data.LocalDatabase
import com.example.data.statistics.NotificationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDatabaseModule {

    @Singleton
    @Provides
    fun provideLocalDatabase(@ApplicationContext context: Context): LocalDatabase =
        LocalDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun provideNotificationDao(roomDatabase: LocalDatabase): NotificationDao =
        roomDatabase.notificationDao()
}
