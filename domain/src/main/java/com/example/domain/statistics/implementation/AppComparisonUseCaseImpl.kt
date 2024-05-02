package com.example.domain.statistics.implementation

import arrow.core.Either
import arrow.core.raise.either
import com.example.data.PackageManagerUtils
import com.example.data.Problem
import com.example.data.report.Report
import com.example.data.report.ReportRepository
import com.example.data.user.implementation.UserDatastore
import com.example.domain.report.AppDto
import com.example.domain.statistics.AppComparisonUseCase
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class AppComparisonUseCaseImpl
@Inject
constructor(
    private val reportRepository: ReportRepository,
    private val userDatastore: UserDatastore,
    private val packageManagerUtils: PackageManagerUtils,
    private val ioDispatcher: CoroutineDispatcher
) : AppComparisonUseCase {
    override suspend fun invoke(
        appName: String
    ): Either<Problem, Pair<ComparisonReportDto, ComparisonReportDto>> =
        withContext(ioDispatcher) {
            either {
                val userId = userDatastore.getUserId().bind()
                val userReports = reportRepository.getReportsByUserId(userId).bind()
                val comparisonReports = getComparisonReports(appName, userReports)
                comparisonReports[0] to comparisonReports[1]
            }
        }

    private fun getComparisonReports(appName: String, userReports: Iterable<Report>) =
        userReports
            .sortedByDescending { it.dateOfRecording.dateOfRecording }
            .take(2)
            .map {
                ComparisonReportDto(
                    it.dateOfRecording.dateOfRecording,
                    it.appReports
                        .filter { app -> app.appName.appName == appName }
                        .map { app ->
                            AppDto(
                                name = app.appName.appName,
                                icon =
                                    packageManagerUtils
                                        .getAppIcon(app.appPackageName.appPackageName)
                                        .fold({ null }) { drawable -> drawable },
                                screenTime = app.screenTime.screenTime,
                                notificationsReceived =
                                    app.notificationsReceived.notificationsReceived,
                                timesOpened = app.timesOpened.timesOpened
                            )
                        }
                        .first()
                )
            }
}

data class ComparisonReportDto(val date: LocalDate, val app: AppDto)
