package org.example.volunteer.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.example.volunteer.data.local.entity.EventEntity

@Dao
interface EventDao {

    @Query("SELECT * FROM events ORDER BY dateMs ASC")
    fun observeAll(): Flow<List<EventEntity>>

    @Query("SELECT * FROM events WHERE category = :category ORDER BY dateMs ASC")
    fun observeByCategory(category: String): Flow<List<EventEntity>>

    @Query("SELECT * FROM events WHERE title LIKE '%' || :query || '%' ORDER BY dateMs ASC")
    fun searchByTitle(query: String): Flow<List<EventEntity>>

    @Query("SELECT * FROM events WHERE category = :category AND title LIKE '%' || :query || '%' ORDER BY dateMs ASC")
    fun searchByCategoryAndTitle(category: String, query: String): Flow<List<EventEntity>>

    @Query("SELECT * FROM events WHERE id = :id LIMIT 1")
    fun observeById(id: String): Flow<EventEntity?>

    @Query("SELECT * FROM events WHERE isUrgent = 1 LIMIT 1")
    fun observeUrgent(): Flow<EventEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(events: List<EventEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: EventEntity)

    @Query("DELETE FROM events")
    suspend fun deleteAll()

    @Query("DELETE FROM events WHERE id = :id")
    suspend fun deleteById(id: String)
}