package com.example.domain.statistics

import android.graphics.drawable.Drawable
import arrow.core.Either
import com.example.data.Problem

interface GetFavouriteAppsUseCase {
    suspend operator fun invoke(): Either<Problem, List<AppDto>>
}

data class AppDto(val appName: String, val icon: Drawable?)
