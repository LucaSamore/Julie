package com.example.data.di

import com.example.data.authentication.AuthenticationService
import com.example.data.authentication.implementation.AuthenticationServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @FirebaseService
    @Singleton
    @Provides
    fun provideAuthenticationService(
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): AuthenticationService = AuthenticationServiceImpl(ioDispatcher)
}

@Retention(AnnotationRetention.BINARY) @Qualifier annotation class FirebaseService
