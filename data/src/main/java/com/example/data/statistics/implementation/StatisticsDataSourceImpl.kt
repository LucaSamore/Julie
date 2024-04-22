package com.example.data.statistics.implementation

import com.example.data.statistics.ScreenTimeDataSource
import com.example.data.statistics.StatisticsDataSource
import javax.inject.Inject

internal class StatisticsDataSourceImpl
@Inject
constructor(private val screenTimeDataSource: ScreenTimeDataSource) : StatisticsDataSource {

    override fun fetchPerAppScreenTime(beginTime: Long, endTime: Long): List<Pair<String, Long>> =
        screenTimeDataSource.getPerAppScreenTime(beginTime, endTime)
}
