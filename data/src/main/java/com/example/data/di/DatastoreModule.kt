package com.example.data.di

import android.content.Context
import com.example.data.user.implementation.UserDatastore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {

    @Singleton
    @Provides
    fun provideUserDatastore(@ApplicationContext context: Context): UserDatastore = UserDatastore(context)
}