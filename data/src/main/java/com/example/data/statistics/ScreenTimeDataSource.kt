package com.example.data.statistics

interface ScreenTimeDataSource {

    /**
     * Fetch the screen time of every app over a period of time
     *
     * @return A list of pairs, where the first element is the app package name and the second
     *   element is the app screen time in milliseconds
     */
    fun getPerAppScreenTime(): List<Pair<String, Long>>

    /**
     * Calculate the total screen time over the last 24h
     *
     * @return Screen time in milliseconds
     */
    fun getCurrentScreenTime(): Long
}
