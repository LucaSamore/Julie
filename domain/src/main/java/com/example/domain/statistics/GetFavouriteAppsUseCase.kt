package com.example.domain.statistics

import android.graphics.drawable.Drawable
import arrow.core.Either
import com.example.data.Problem

interface GetFavouriteAppsUseCase {
    suspend operator fun invoke(): Either<Problem, List<FavouriteAppDto>>
}

data class FavouriteAppDto(val appName: String, val appScreenTime: Long, val icon: Drawable?)
