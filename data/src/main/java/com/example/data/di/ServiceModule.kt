package com.example.data.di

import com.example.data.authentication.AuthenticationService
import com.example.data.authentication.implementation.AuthenticationServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @FirebaseService
    @Provides
    fun provideAuthenticationService(
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): AuthenticationService = AuthenticationServiceImpl(ioDispatcher)
}

@Qualifier @Retention(AnnotationRetention.BINARY) annotation class FirebaseService
