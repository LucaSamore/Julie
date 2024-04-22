package com.example.data.statistics

interface StatisticsDataSource {

    fun fetchPerAppScreenTime(beginTime: Long, endTime: Long): List<Pair<String, Long>>
}
