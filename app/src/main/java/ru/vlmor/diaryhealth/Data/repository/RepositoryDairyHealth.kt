package ru.vlmor.diaryhealth.data.repository

import androidx.lifecycle.LiveData
import ru.vlmor.diaryhealth.data.model.Dairy

interface RepositoryDairyHealth{
    fun getAll(): LiveData<List<Dairy>>
    fun get(id: Long): LiveData<Dairy>
    fun insert(dairy: Dairy): Long
    fun update(dairy: Dairy)
    fun delete(dairy: Dairy)
}