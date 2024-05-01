package com.example.domain.report

import com.example.data.PackageManagerUtils
import com.example.data.di.FirebaseRepository
import com.example.data.di.IoDispatcher
import com.example.data.report.ReportRepository
import com.example.data.user.implementation.UserDatastore
import com.example.domain.report.implementation.GetUserReportsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object ReportModule {

    @Singleton
    @Provides
    fun provideGetUserReportsUseCase(
        @FirebaseRepository reportRepository: ReportRepository,
        userDatastore: UserDatastore,
        packageManagerUtils: PackageManagerUtils,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): GetUserReportsUseCase =
        GetUserReportsUseCaseImpl(
            reportRepository,
            userDatastore,
            packageManagerUtils,
            ioDispatcher
        )
}
