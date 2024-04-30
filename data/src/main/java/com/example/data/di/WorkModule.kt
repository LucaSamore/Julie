package com.example.data.di

import android.content.Context
import com.example.data.WorkerManager
import com.example.data.implementation.WorkerManagerImpl
import com.example.data.user.implementation.UserDatastore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkModule {

    @Singleton
    @Provides
    fun provideWorkerManager(
        userDatastore: UserDatastore,
        @ApplicationContext context: Context
    ): WorkerManager = WorkerManagerImpl(userDatastore, context)
}
