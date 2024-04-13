package com.example.domain.di

import com.example.data.authentication.AuthenticationService
import com.example.data.di.FirebaseService
import com.example.domain.authentication.SignInUseCase
import com.example.domain.authentication.implementation.SignInUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideSignInUseCase(@FirebaseService authenticationService: AuthenticationService): SignInUseCase =
        SignInUseCaseImpl(authenticationService)
}