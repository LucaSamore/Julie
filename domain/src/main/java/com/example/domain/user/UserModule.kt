package com.example.domain.user

import com.example.data.di.FirebaseRepository
import com.example.data.di.IoDispatcher
import com.example.data.user.UserProfileRepository
import com.example.data.user.implementation.UserDatastore
import com.example.domain.user.implementation.CacheUserIdUseCaseImpl
import com.example.domain.user.implementation.GetUserProfileGamificationDataUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Singleton
    @Provides
    fun provideCacheUserIdUseCase(userDatastore: UserDatastore): CacheUserIdUseCase =
        CacheUserIdUseCaseImpl(userDatastore)

    @Singleton
    @Provides
    fun provideGetUserProfileGamificationDataUseCase(
        @FirebaseRepository userProfileRepository: UserProfileRepository,
        userDatastore: UserDatastore,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): GetUserProfileGamificationDataUseCase =
        GetUserProfileGamificationDataUseCaseImpl(
            userProfileRepository,
            userDatastore,
            ioDispatcher
        )
}
