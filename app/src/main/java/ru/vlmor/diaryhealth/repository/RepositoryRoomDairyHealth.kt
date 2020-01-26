package ru.vlmor.diaryhealth.repository

import androidx.lifecycle.LiveData
import ru.vlmor.diaryhealth.data.database.DairyDatabase
import ru.vlmor.diaryhealth.data.model.Dairy
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RepositoryRoomDairyHealth (val dairyDatabase: DairyDatabase) : RepositoryDairyHealth{

    override suspend fun getAll(): List<Dairy> = suspendCoroutine{c ->
        c.resume(dairyDatabase.dairyDao().getAll())
    }

    override suspend fun get(id: Long): Dairy = suspendCoroutine { c ->
        c.resume(dairyDatabase.dairyDao().get(id))
    }

    override suspend fun insert(dairy: Dairy): Long = suspendCoroutine{c ->
        c.resume(dairyDatabase.dairyDao().insert(dairy))
    }

    override suspend fun update(dairy: Dairy): Dairy = suspendCoroutine {c -> {
            dairyDatabase.dairyDao().update(dairy)
            c.resume(dairyDatabase.dairyDao().get(dairy.id!!))
        }
    }

    override suspend fun delete(dairy: Dairy) = suspendCoroutine<Unit> {c ->
        dairyDatabase.dairyDao().delete(dairy)
    }

    override suspend fun deleteAll() = suspendCoroutine<Unit> {c ->
        dairyDatabase.dairyDao().deleteAll()
    }
}