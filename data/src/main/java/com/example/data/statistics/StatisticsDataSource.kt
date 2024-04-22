package com.example.data.statistics

interface StatisticsDataSource {

    fun fetchPerAppScreenTime(): List<Pair<String, Long>>
}
