package com.example.domain.di

import com.example.data.authentication.AuthenticationService
import com.example.data.di.FirebaseRepository
import com.example.data.di.FirebaseService
import com.example.data.di.IoDispatcher
import com.example.data.user.UserProfileRepository
import com.example.domain.authentication.SignInUseCase
import com.example.domain.authentication.SignUpUseCase
import com.example.domain.authentication.implementation.SignInUseCaseImpl
import com.example.domain.authentication.implementation.SignUpUseCaseImpl
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
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): SignInUseCase = SignInUseCaseImpl(authenticationService, ioDispatcher)

    @Singleton
    @Provides
    fun provideSignUpUseCase(
        @FirebaseService authenticationService: AuthenticationService,
        @FirebaseRepository userProfileRepository: UserProfileRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): SignUpUseCase = SignUpUseCaseImpl(authenticationService, userProfileRepository, ioDispatcher)
}
