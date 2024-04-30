package com.example.data.report

import arrow.core.Either
import arrow.core.NonEmptyList
import com.example.data.Problem
import java.time.LocalDateTime

interface UploadReportService {
    suspend operator fun invoke(dateTime: LocalDateTime): Either<NonEmptyList<Problem>, Report>
}
