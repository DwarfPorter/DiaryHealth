package ru.vlmor.diaryhealth.Dao

import androidx.room.*
import ru.vlmor.diaryhealth.Model.Dairy

@Dao
interface DairyHealthDao{
    @Query("SELECT * FROM Dairy")
    fun getAll(): List<Dairy>

    @Query("SELECT * FROM Dairy WHERE id = :id")
    fun getDairy(id: Long): Dairy

    @Insert
    fun insertDairy(dairy: Dairy): Long

    @Update
    fun updateDairy(dairy: Dairy)

    @Delete
    fun deleteDairy(dairy: Dairy)
}