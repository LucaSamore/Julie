package com.example.data.statistics

interface ScreenTimeDataSource {

    /**
     * Fetch the screen time of every app over the last 24h
     *
     * @return A map, where the key is the app package name and the value is the app screen time in
     *   seconds
     */
    fun getPerAppScreenTime(): Map<String, Long>

    /**
     * Calculate the total screen time over the last 24h
     *
     * @return Screen time in seconds
     */
    fun getCurrentScreenTime(): Long
}
