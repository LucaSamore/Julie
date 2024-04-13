package com.example.data.di

import com.example.data.user.UserProfileRepository
import com.example.data.user.implementation.UserProfileRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @FirebaseRepository
    @Singleton
    @Provides
    fun provideUserProfileRepository(
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): UserProfileRepository = UserProfileRepositoryImpl(ioDispatcher)
}

@Retention(AnnotationRetention.BINARY) @Qualifier annotation class FirebaseRepository