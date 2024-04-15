package com.example.domain.di

import com.example.data.authentication.AuthenticationService
import com.example.data.di.FirebaseRepository
import com.example.data.di.FirebaseService
import com.example.data.di.IoDispatcher
import com.example.data.user.UserProfileRepository
import com.example.data.user.implementation.UserDatastore
import com.example.domain.authentication.PasswordResetUseCase
import com.example.domain.authentication.SignInUseCase
import com.example.domain.authentication.SignOutUseCase
import com.example.domain.authentication.SignUpUseCase
import com.example.domain.authentication.implementation.PasswordResetUseCaseImpl
import com.example.domain.authentication.implementation.SignInUseCaseImpl
import com.example.domain.authentication.implementation.SignOutUseCaseImpl
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
        @FirebaseRepository userProfileRepository: UserProfileRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        userDatastore: UserDatastore
    ): SignInUseCase =
        SignInUseCaseImpl(authenticationService, userProfileRepository, ioDispatcher, userDatastore)

    @Singleton
    @Provides
    fun provideSignUpUseCase(
        @FirebaseService authenticationService: AuthenticationService,
        @FirebaseRepository userProfileRepository: UserProfileRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        userDatastore: UserDatastore
    ): SignUpUseCase =
        SignUpUseCaseImpl(authenticationService, userProfileRepository, ioDispatcher, userDatastore)

    @Singleton
    @Provides
    fun provideSignOutUseCase(
        @FirebaseService authenticationService: AuthenticationService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        userDatastore: UserDatastore
    ): SignOutUseCase = SignOutUseCaseImpl(authenticationService, ioDispatcher, userDatastore)

    @Singleton
    @Provides
    fun providePasswordResetUseCase(
        @FirebaseService authenticationService: AuthenticationService,
        @FirebaseRepository userProfileRepository: UserProfileRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): PasswordResetUseCase =
        PasswordResetUseCaseImpl(authenticationService, userProfileRepository, ioDispatcher)
}
