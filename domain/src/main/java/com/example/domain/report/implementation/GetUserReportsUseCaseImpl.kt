package com.example.domain.report.implementation

import arrow.core.Either
import arrow.core.raise.either
import com.example.data.PackageManagerUtils
import com.example.data.Problem
import com.example.data.report.ReportRepository
import com.example.data.user.implementation.UserDatastore
import com.example.domain.report.AppDto
import com.example.domain.report.GetUserReportsUseCase
import com.example.domain.report.ReportDto
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class GetUserReportsUseCaseImpl
@Inject
constructor(
    private val reportRepository: ReportRepository,
    private val userDatastore: UserDatastore,
    private val packageManagerUtils: PackageManagerUtils,
    private val ioDispatcher: CoroutineDispatcher
) : GetUserReportsUseCase {
    override suspend fun invoke(): Either<Problem, Iterable<ReportDto>> =
        withContext(ioDispatcher) {
            either {
                val userId = userDatastore.getUserId().bind()
                val reports = reportRepository.getReportsByUserId(userId).bind()
                reports.map {
                    ReportDto(
                        date = it.dateOfRecording.dateOfRecording,
                        appReports =
                            it.appReports.map { appReport ->
                                AppDto(
                                    name = appReport.appName.appName,
                                    icon =
                                        packageManagerUtils
                                            .getAppIcon(appReport.appPackageName.appPackageName)
                                            .fold({ null }) { drawable -> drawable },
                                    screenTime = appReport.screenTime.screenTime,
                                    notificationsReceived =
                                        appReport.notificationsReceived.notificationsReceived,
                                    timesOpened = appReport.timesOpened.timesOpened
                                )
                            }
                    )
                }
            }
        }
}
