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

    override suspend fun getReportsByUserId(
        userId: UserId
    ): Either<RepositoryProblem, Iterable<Report>> {
        return Either.catch {
                db.collection(FirestoreReportDto.COLLECTION)
                    .whereEqualTo("userId", userId.userId)
                    .get()
                    .await()
                    .toObjects(FirestoreReportDto::class.java)
                    .mapNotNull { FirestoreReportDto.toEntity(it) }
                    .sortedBy { it.dateOfRecording.dateOfRecording }
            }
            .mapLeft { RepositoryProblem.fromThrowable(it) }
    }

    override suspend fun getFavouriteApps(
        userId: UserId,
        timeSpanInDays: Int,
        top: Int
    ): Either<RepositoryProblem, Map<AppPackageName, Long>> {
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

    private fun getTopUsedApps(apps: List<Report>, top: Int): Map<AppPackageName, Long> {
        return apps
            .flatMap { it.appReports }
            .groupBy { it.appPackageName }
            .mapValues {
                (it.value.sumOf { appReport -> appReport.screenTime.screenTime }.toDouble() /
                        it.value.size)
                    .toLong()
            }
            .toList()
            .sortedByDescending { it.second }
            .take(top)
            .toMap()
    }
}
