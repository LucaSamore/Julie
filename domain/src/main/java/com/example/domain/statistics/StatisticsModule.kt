package com.example.domain.statistics

import com.example.data.di.FirebaseRepository
import com.example.data.di.IoDispatcher
import com.example.data.report.ReportRepository
import com.example.data.statistics.StatisticsDataSource
import com.example.data.user.implementation.UserDatastore
import com.example.domain.statistics.implementation.GetCurrentScreenTimeUseCaseImpl
import com.example.domain.statistics.implementation.GetFavouriteAppsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object StatisticsModule {

    @Singleton
    @Provides
    fun provideGetCurrentScreenTimeUseCase(
        statisticsDataSource: StatisticsDataSource,
    ): GetCurrentScreenTimeUseCase = GetCurrentScreenTimeUseCaseImpl(statisticsDataSource)

    @Singleton
    @Provides
    fun provideGetFavouriteAppsUseCase(
        @FirebaseRepository reportRepository: ReportRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        userDatastore: UserDatastore
    ): GetFavouriteAppsUseCase =
        GetFavouriteAppsUseCaseImpl(reportRepository, ioDispatcher, userDatastore)
}
