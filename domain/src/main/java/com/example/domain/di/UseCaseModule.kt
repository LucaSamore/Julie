package com.example.domain.di

import com.example.data.WorkerManager
import com.example.data.authentication.AuthenticationService
import com.example.data.di.FirebaseRepository
import com.example.data.di.FirebaseService
import com.example.data.di.IoDispatcher
import com.example.data.user.UserProfileRepository
import com.example.domain.authentication.PasswordResetUseCase
import com.example.domain.authentication.SignInUseCase
import com.example.domain.authentication.SignOutUseCase
import com.example.domain.authentication.SignUpUseCase
import com.example.domain.authentication.VerifyEmailUseCase
import com.example.domain.authentication.implementation.PasswordResetUseCaseImpl
import com.example.domain.authentication.implementation.SignInUseCaseImpl
import com.example.domain.authentication.implementation.SignOutUseCaseImpl
import com.example.domain.authentication.implementation.SignUpUseCaseImpl
import com.example.domain.authentication.implementation.VerifyEmailUseCaseImpl
import com.example.domain.report.ScheduleUploadReportWorkerUseCase
import com.example.domain.report.implementation.ScheduleUploadReportWorkerUseCaseImpl
import com.example.domain.user.CacheUserIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideSignInUseCase(
        @FirebaseService authenticationService: AuthenticationService,
        @FirebaseRepository userProfileRepository: UserProfileRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        cacheUserIdUseCase: CacheUserIdUseCase,
        workerManager: WorkerManager
    ): SignInUseCase =
        SignInUseCaseImpl(
            authenticationService,
            userProfileRepository,
            ioDispatcher,
            cacheUserIdUseCase,
            workerManager
        )

    @Singleton
    @Provides
    fun provideSignUpUseCase(
        @FirebaseService authenticationService: AuthenticationService,
        @FirebaseRepository userProfileRepository: UserProfileRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        cacheUserIdUseCase: CacheUserIdUseCase
    ): SignUpUseCase =
        SignUpUseCaseImpl(
            authenticationService,
            userProfileRepository,
            ioDispatcher,
            cacheUserIdUseCase
        )

    @Singleton
    @Provides
    fun provideSignOutUseCase(
        @FirebaseService authenticationService: AuthenticationService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        workerManager: WorkerManager
    ): SignOutUseCase = SignOutUseCaseImpl(authenticationService, ioDispatcher, workerManager)

    @Singleton
    @Provides
    fun providePasswordResetUseCase(
        @FirebaseService authenticationService: AuthenticationService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): PasswordResetUseCase = PasswordResetUseCaseImpl(authenticationService, ioDispatcher)

    @Singleton
    @Provides
    fun provideVerifyEmailUseCase(
        @FirebaseService authenticationService: AuthenticationService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): VerifyEmailUseCase = VerifyEmailUseCaseImpl(authenticationService, ioDispatcher)

    @Singleton
    @Provides
    fun provideScheduleUploadReportWorkerUseCase(
        workerManager: WorkerManager
    ): ScheduleUploadReportWorkerUseCase = ScheduleUploadReportWorkerUseCaseImpl(workerManager)
}
