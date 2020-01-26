package ru.vlmor.diaryhealth.repository

import ru.vlmor.diaryhealth.data.model.Dairy

interface RepositoryDairyHealth{
    suspend fun getAll(): List<Dairy>
    suspend fun get(id: Long): Dairy
    suspend fun insert(dairy: Dairy): Long
    suspend fun update(dairy: Dairy): Dairy
    suspend fun delete(dairy: Dairy)
    suspend fun deleteAll()
}