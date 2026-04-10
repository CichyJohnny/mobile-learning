package com.example.mobiles1

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "routes")
data class Route(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val title: String,
    val description: String,
    val elapsedTime: Long = 0L,
    val isRunning: Boolean = false,
    val lastStartTime: Long = 0L
)
