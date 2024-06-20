package com.example.domain.statistics.implementation

import arrow.core.Either
import arrow.core.raise.either
import com.example.data.PackageManagerUtils
import com.example.data.Problem
import com.example.data.report.ReportRepository
import com.example.data.user.implementation.UserDatastore
import com.example.domain.statistics.FavouriteAppDto
import com.example.domain.statistics.GetFavouriteAppsUseCase
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class GetFavouriteAppsUseCaseImpl
@Inject
constructor(
    private val reportRepository: ReportRepository,
    private val ioDispatcher: CoroutineDispatcher,
    private val userDatastore: UserDatastore,
    private val packageManagerUtils: PackageManagerUtils
) : GetFavouriteAppsUseCase {

    override suspend fun invoke(): Either<Problem, List<FavouriteAppDto>> {
        return withContext(ioDispatcher) {
            either {
                val userId = userDatastore.getUserId().bind()
                reportRepository
                    .getFavouriteApps(
                        userId = userId,
                        timeSpanInDays = TIME_SPAN_IN_DAYS,
                        top = TOP
                    )
                    .bind()
                    .map {
                        FavouriteAppDto(
                            appName = getAppName(it.key.appPackageName),
                            appScreenTime = it.value,
                            icon = getIcon(it.key.appPackageName)
                        )
                    }
            }
        }
    }

    private fun getAppName(packageName: String) =
        packageManagerUtils.getAppNameFromPackageName(packageName)

    private fun getIcon(packageName: String) =
        packageManagerUtils.getAppIcon(packageName).fold({ null }) { drawable -> drawable }

    companion object {
        const val TIME_SPAN_IN_DAYS = 60
        const val TOP = 5
    }
}
