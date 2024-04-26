package com.example.data.di

import com.example.data.PackageManagerUtils
import com.example.data.authentication.AuthenticationService
import com.example.data.authentication.implementation.AuthenticationServiceImpl
import com.example.data.report.ReportRepository
import com.example.data.report.UploadReportService
import com.example.data.report.implementation.UploadReportServiceImpl
import com.example.data.statistics.StatisticsDataSource
import com.example.data.user.implementation.UserDatastore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @FirebaseService
    @Singleton
    @Provides
    fun provideAuthenticationService(): AuthenticationService = AuthenticationServiceImpl()

    @Singleton
    @Provides
    fun provideUploadReportService(
        statisticsDataSource: StatisticsDataSource,
        @FirebaseRepository reportRepository: ReportRepository,
        userDatastore: UserDatastore,
        packageManagerUtils: PackageManagerUtils
    ): UploadReportService =
        UploadReportServiceImpl(
            statisticsDataSource,
            reportRepository,
            userDatastore,
            packageManagerUtils
        )
}

@Retention(AnnotationRetention.BINARY) @Qualifier annotation class FirebaseService
