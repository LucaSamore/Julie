package com.example.domain.statistics.implementation

import com.example.data.statistics.StatisticsDataSource
import com.example.data.today
import com.example.domain.statistics.GetCurrentScreenTimeUseCase
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

internal class GetCurrentScreenTimeUseCaseImpl
@Inject
constructor(private val statisticsDataSource: StatisticsDataSource) : GetCurrentScreenTimeUseCase {

    override fun invoke(): Long = statisticsDataSource.getScreenTime(today(), now())

    companion object {
        private fun now() =
            Instant.ofEpochMilli(System.currentTimeMillis())
                .atZone(ZoneId.of("UTC"))
                .toLocalDateTime()
    }
}
