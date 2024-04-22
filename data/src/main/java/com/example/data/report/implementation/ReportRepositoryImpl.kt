package com.example.data.report.implementation

import arrow.core.Either
import com.example.data.RepositoryProblem
import com.example.data.report.Report
import com.example.data.report.ReportId
import com.example.data.report.ReportRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
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
}
