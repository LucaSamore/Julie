package com.example.domain.statistics.implementation

import com.example.data.PackageManagerUtils
import com.example.data.statistics.StatisticsDataSource
import com.example.data.today
import com.example.domain.report.AppDto
import com.example.domain.statistics.GetAppsCurrentStatsUseCase
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class GetAppsCurrentStatsUseCaseImpl
@Inject
constructor(
    private val statisticsDataSource: StatisticsDataSource,
    private val packageManagerUtils: PackageManagerUtils,
    private val ioDispatcher: CoroutineDispatcher
) : GetAppsCurrentStatsUseCase {

    override suspend fun invoke(): List<AppDto> =
        withContext(ioDispatcher) {
            val installedAppPackageNames = packageManagerUtils.getInstalledAppPackageNames()
            val screenTimes = statisticsDataSource.fetchPerAppScreenTime(today(), now())
            val notifications = statisticsDataSource.fetchPerAppNotificationsReceived(today())
            val timesOpened = statisticsDataSource.fetchPerAppTimesOpened(today(), now())

            installedAppPackageNames.map {
                val appName = packageManagerUtils.getAppNameFromPackageName(it)
                val icon = packageManagerUtils.getAppIcon(it).getOrNull()
                AppDto(
                    name = appName,
                    icon = icon,
                    screenTime = screenTimes[it] ?: 0L,
                    notificationsReceived = notifications[it] ?: 0,
                    timesOpened = timesOpened[it] ?: 0
                )
            }
        }

    companion object {
        private fun now() =
            Instant.ofEpochMilli(System.currentTimeMillis())
                .atZone(ZoneId.of("UTC"))
                .toLocalDateTime()
    }
}
