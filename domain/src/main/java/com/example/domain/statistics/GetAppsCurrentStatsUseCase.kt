package com.example.domain.statistics

import com.example.domain.report.AppDto

interface GetAppsCurrentStatsUseCase {
    suspend operator fun invoke(): List<AppDto>
}
