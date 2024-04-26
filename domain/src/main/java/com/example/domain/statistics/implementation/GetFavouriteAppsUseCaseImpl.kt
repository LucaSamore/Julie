package com.example.domain.statistics.implementation

import arrow.core.Either
import arrow.core.raise.either
import com.example.data.PackageManagerUtils
import com.example.data.Problem
import com.example.data.report.ReportRepository
import com.example.data.user.implementation.UserDatastore
import com.example.domain.statistics.AppDto
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

    override suspend fun invoke(): Either<Problem, List<AppDto>> =
        withContext(ioDispatcher) {
            either {
                val userId = userDatastore.getUserId().bind()
                reportRepository
                    .getFavouriteApps(userId = userId, timeSpanInDays = 7, top = 10)
                    .bind()
                    .map {
                        AppDto(
                            appName =
                                packageManagerUtils.getAppNameFromPackageName(it.appPackageName),
                            icon =
                                packageManagerUtils
                                    .getAppIcon(it.appPackageName)
                                    .fold({ null }, { drawable -> drawable })
                        )
                    }
            }
        }
}
