package com.example.data.report.implementation

import arrow.core.Either
import com.example.data.RepositoryProblem
import com.example.data.report.AppName
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

    override suspend fun create(entity: Report): Either<RepositoryProblem, Report> =
        Either.catch {
                db.collection(FirestoreReportDto.COLLECTION)
                    .document(entity.id.reportId)
                    .set(FirestoreReportDto.fromEntity(entity))
                    .await()
            }
            .mapLeft { RepositoryProblem.fromThrowable(it) }
            .map { entity }

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
    ): Either<RepositoryProblem, List<AppName>> =
        Either.catch {
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
                    .map { FirestoreReportDto.toEntity(it) }
            }
            .mapLeft { RepositoryProblem.fromThrowable(it) }
            .map {
                it.flatMap { report -> report?.appReports!! }
                    .groupBy { appReport -> appReport.appName }
                    .mapValues { group ->
                        group.value
                            .sumOf { appReport -> appReport.screenTime.screenTime }
                            .toDouble() / group.value.size
                    }
                    .toList()
                    .sortedByDescending { pair -> pair.second }
                    .map { pair -> pair.first }
                    .take(top)
            }
}
