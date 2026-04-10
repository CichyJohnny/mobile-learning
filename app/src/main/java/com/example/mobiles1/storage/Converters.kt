package com.example.mobiles1.storage

import androidx.room.TypeConverter
import java.util.UUID

class Converters {
    @TypeConverter
    fun fromUUID(uuid: UUID): String {
        return uuid.toString()
    }

    @TypeConverter
    fun uuidFromString(string: String): UUID {
        return UUID.fromString(string)
    }
}