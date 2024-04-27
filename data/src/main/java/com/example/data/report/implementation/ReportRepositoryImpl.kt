package com.example.data.report.implementation

import arrow.core.Either
import com.example.data.RepositoryProblem
import com.example.data.report.AppPackageName
import com.example.data.report.Report
import com.example.data.report.ReportId
import com.example.data.report.ReportRepository
import com.example.data.user.UserId
import com.google.firebase.Firebase
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import java.time.LocalDate
import kotlinx.coroutines.tasks.await

internal class ReportRepositoryImpl : ReportRepository {

    private val db: FirebaseFirestore = Firebase.firestore

    override suspend fun create(entity: Report): Either<RepositoryProblem, Report> {
        return Either.catch {
                db.collection(FirestoreReportDto.COLLECTION)
                    .document(entity.id.reportId)
                    .set(FirestoreReportDto.fromEntity(entity))
                    .await()
            }
            .mapLeft { RepositoryProblem.fromThrowable(it) }
            .map { entity }
    }

    override suspend fun findMany(): Either<RepositoryProblem, Iterable<Report>> {
        TODO("Not yet implemented")
    }

    override suspend fun findOne(id: ReportId): Either<RepositoryProblem, Report> {
        TODO("Not yet implemented")
    }

    override suspend fun update(entity: Report): Either<RepositoryProblem, Report> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: ReportId): Either<RepositoryProblem, Report> {
        TODO("Not yet implemented")
    }

    override suspend fun getFavouriteApps(
        userId: UserId,
        timeSpanInDays: Int,
        top: Int
    ): Either<RepositoryProblem, List<AppPackageName>> {
        return Either.catch {
                db.collection(FirestoreReportDto.COLLECTION)
                    .where(
                        Filter.and(
                            Filter.equalTo("userId", userId.userId),
                            Filter.greaterThanOrEqualTo(
                                "date",
                                LocalDate.now().minusDays(timeSpanInDays.toLong()).toString()
                            )
                        )
                    )
                    .get()
                    .await()
                    .toObjects(FirestoreReportDto::class.java)
                    .mapNotNull { FirestoreReportDto.toEntity(it) }
            }
            .mapLeft { RepositoryProblem.fromThrowable(it) }
            .map { getTopUsedApps(it, top) }
    }

    private fun getTopUsedApps(apps: List<Report>, top: Int): List<AppPackageName> {
        return apps
            .flatMap { it.appReports }
            .groupBy { it.appPackageName }
            .mapValues {
                it.value.sumOf { appReport -> appReport.screenTime.screenTime }.toDouble() /
                    it.value.size
            }
            .toList()
            .sortedByDescending { it.second }
            .map { it.first }
            .take(top)
    }
}
