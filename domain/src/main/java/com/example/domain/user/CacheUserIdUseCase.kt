package com.example.domain.user

interface CacheUserIdUseCase {
    suspend operator fun invoke(userId: String)
}
