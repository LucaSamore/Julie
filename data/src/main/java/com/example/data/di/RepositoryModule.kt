package com.example.data.di

import com.example.data.user.UserProfileRepository
import com.example.data.user.implementation.UserProfileRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @FirebaseRepository
    @Singleton
    @Provides
    fun provideUserProfileRepository(): UserProfileRepository = UserProfileRepositoryImpl()
}

@Retention(AnnotationRetention.BINARY) @Qualifier annotation class FirebaseRepository
