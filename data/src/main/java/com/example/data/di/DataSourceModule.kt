package com.example.data.di

import android.content.Context
import com.example.data.statistics.ScreenTimeDataSource
import com.example.data.statistics.StatisticsDataSource
import com.example.data.statistics.implementation.ScreenTimeDataSourceImpl
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
        screenTimeDataSource: ScreenTimeDataSource
    ): StatisticsDataSource = StatisticsDataSourceImpl(screenTimeDataSource)

    @Singleton
    @Provides
    fun provideScreenTimeDataSource(@ApplicationContext context: Context): ScreenTimeDataSource =
        ScreenTimeDataSourceImpl(context)
}
