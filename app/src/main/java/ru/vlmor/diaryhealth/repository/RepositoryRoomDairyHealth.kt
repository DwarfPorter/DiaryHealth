package ru.vlmor.diaryhealth.repository

import androidx.lifecycle.LiveData
import ru.vlmor.diaryhealth.data.database.DairyDatabase
import ru.vlmor.diaryhealth.data.model.Dairy

class RepositoryRoomDairyHealth (val dairyDatabase: DairyDatabase) : RepositoryDairyHealth{

    override fun getAll(): LiveData<List<Dairy>> {
        return dairyDatabase.dairyDao().getAll()
    }

    override fun get(id: Long): LiveData<Dairy> {
        return dairyDatabase.dairyDao().get(id)
    }

    override fun insert(dairy: Dairy): Long {
        return dairyDatabase.dairyDao().insert(dairy)
    }

    override fun update(dairy: Dairy): LiveData<Dairy> {
        dairyDatabase.dairyDao().update(dairy)
        return dairyDatabase.dairyDao().get(dairy.id!!)
    }

    override fun delete(dairy: Dairy) {
        dairyDatabase.dairyDao().delete(dairy)
    }

    override fun deleteAll() {
        dairyDatabase.dairyDao().deleteAll()
    }

}