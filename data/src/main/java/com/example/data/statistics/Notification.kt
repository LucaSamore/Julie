package com.example.data.statistics

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications_received")
data class Notification(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "package_name") val packageName: String,
    @ColumnInfo(name = "date") val date: String
)
