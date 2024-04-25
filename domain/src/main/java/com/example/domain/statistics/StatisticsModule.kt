package com.example.domain.statistics

import com.example.data.statistics.StatisticsDataSource
import com.example.domain.statistics.implementation.GetCurrentScreenTimeUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StatisticsModule {

    @Singleton
    @Provides
    fun provideGetCurrentScreenTimeUseCase(
        statisticsDataSource: StatisticsDataSource,
    ): GetCurrentScreenTimeUseCase = GetCurrentScreenTimeUseCaseImpl(statisticsDataSource)
}
