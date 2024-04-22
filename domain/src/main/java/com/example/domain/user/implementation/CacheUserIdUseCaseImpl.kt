package com.example.domain.user.implementation

import com.example.data.user.implementation.UserDatastore
import com.example.domain.user.CacheUserIdUseCase
import javax.inject.Inject

internal class CacheUserIdUseCaseImpl
@Inject
constructor(private val userDatastore: UserDatastore) : CacheUserIdUseCase {

    override suspend fun invoke(userId: String) = userDatastore.saveUserIdToDataStore(userId)
}
