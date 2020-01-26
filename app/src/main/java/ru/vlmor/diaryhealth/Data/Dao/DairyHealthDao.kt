package ru.vlmor.diaryhealth.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.vlmor.diaryhealth.data.model.Dairy

@Dao
interface DairyHealthDao{
    @Query("SELECT * FROM Dairy")
    fun getAll(): List<Dairy>

    @Query("SELECT * FROM Dairy WHERE id = :id")
    fun get(id: Long): Dairy

    @Insert
    fun insert(dairy: Dairy): Long

    @Update
    fun update(dairy: Dairy)

    @Delete
    fun delete(dairy: Dairy)

    @Query("DELETE FROM Dairy")
    fun deleteAll()
}