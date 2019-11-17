package ru.vlmor.diaryhealth.Dao

import androidx.room.*
import ru.vlmor.diaryhealth.Model.Dairy

@Dao
interface DairyHealthDao{
    @Query("SELECT * FROM Dairy")
    suspend fun getAll(): List<Dairy>

    @Query("SELECT * FROM Dairy WHERE id = :id")
    suspend fun get(id: Long): Dairy

    @Insert
    suspend fun insert(dairy: Dairy): Long

    @Update
    suspend fun update(dairy: Dairy)

    @Delete
    suspend fun delete(dairy: Dairy)
}