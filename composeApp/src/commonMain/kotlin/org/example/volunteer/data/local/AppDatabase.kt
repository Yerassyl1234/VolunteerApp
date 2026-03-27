package org.example.volunteer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import org.example.volunteer.data.local.dao.EventDao
import org.example.volunteer.data.local.entity.EventEntity

@Database(
    entities = [EventEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
}