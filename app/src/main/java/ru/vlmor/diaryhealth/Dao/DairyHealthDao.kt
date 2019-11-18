package ru.vlmor.diaryhealth.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.vlmor.diaryhealth.Model.Dairy

@Dao
interface DairyHealthDao{
    @Query("SELECT * FROM Dairy")
    fun getAll(): LiveData<List<Dairy>>

    @Query("SELECT * FROM Dairy WHERE id = :id")
    fun get(id: Long): LiveData<Dairy>

    @Insert
    fun insert(dairy: Dairy): Long

    @Update
    fun update(dairy: Dairy)

    @Delete
    fun delete(dairy: Dairy)
}