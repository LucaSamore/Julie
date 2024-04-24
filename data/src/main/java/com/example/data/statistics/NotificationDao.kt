package com.example.data.statistics

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE) suspend fun insert(notification: Notification)

    @Query(
        "SELECT package_name as packageName, COUNT(*) AS count " +
            "FROM notifications_received " +
            "WHERE date = :date " +
            "GROUP BY package_name"
    )
    suspend fun getByDay(date: String): List<DailyNotifications>

    @Query("DELETE FROM notifications_received") suspend fun deleteAll()
}

data class DailyNotifications(val packageName: String, val count: Int)
