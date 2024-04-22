package com.example.data.statistics.implementation

import android.app.usage.UsageStatsManager
import android.content.Context
import com.example.data.statistics.ScreenTimeDataSource
import javax.inject.Inject

internal class ScreenTimeDataSourceImpl @Inject constructor(context: Context) :
    ScreenTimeDataSource {

    private val usageStatsManager =
        context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    override fun getPerAppScreenTime(beginTime: Long, endTime: Long): List<Pair<String, Long>> =
        usageStatsManager
            .queryUsageStats(UsageStatsManager.INTERVAL_DAILY, beginTime, endTime)
            .map { Pair(it.packageName, it.totalTimeVisible) }
            .filter { it.second > 0 }
            .sortedByDescending { it.second }

    override fun getCurrentScreenTime(): Long {
        val endTime = System.currentTimeMillis()
        val beginTime = endTime - (24 * 60 * 60 * 1000)
        return usageStatsManager
            .queryUsageStats(UsageStatsManager.INTERVAL_DAILY, beginTime, endTime)
            .sumOf { it.totalTimeVisible }
    }
}
