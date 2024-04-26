package com.example.data.di

import android.content.Context
import com.example.data.PackageManagerUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {

    @Singleton
    @Provides
    fun providePackageManagerUtils(@ApplicationContext context: Context): PackageManagerUtils =
        PackageManagerUtils(context)
}
