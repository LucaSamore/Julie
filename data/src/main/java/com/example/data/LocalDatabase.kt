package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.statistics.Notification
import com.example.data.statistics.NotificationDao

@Database(entities = [Notification::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun notificationDao(): NotificationDao

    companion object {
        @Volatile private var INSTANCE: LocalDatabase? = null

        fun getDatabase(context: Context): LocalDatabase {
            return INSTANCE
                ?: synchronized(this) {
                    val instance =
                        Room.databaseBuilder(
                                context.applicationContext,
                                LocalDatabase::class.java,
                                "julie_database",
                            )
                            .build()
                    INSTANCE = instance
                    instance
                }
        }
    }
}
