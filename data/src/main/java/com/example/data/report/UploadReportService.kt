package com.example.data.report

import arrow.core.Either
import arrow.core.NonEmptyList
import com.example.data.Problem

interface UploadReportService {
    suspend operator fun invoke(): Either<NonEmptyList<Problem>, Report>
}
