package com.example.domain.statistics.implementation

import com.example.data.statistics.StatisticsDataSource
import com.example.domain.statistics.GetCurrentScreenTimeUseCase
import javax.inject.Inject

internal class GetCurrentScreenTimeUseCaseImpl
@Inject
constructor(private val statisticsDataSource: StatisticsDataSource) : GetCurrentScreenTimeUseCase {

    override fun invoke(): Long = statisticsDataSource.getCurrentScreenTime()
}
