package ru.vlmor.diaryhealth.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import ru.vlmor.diaryhealth.data.database.DairyDatabase
import ru.vlmor.diaryhealth.data.model.Dairy
import ru.vlmor.diaryhealth.repository.RepositoryRoomDairyHealth

class DairiesViewModel(application: Application) : AndroidViewModel(application) {
    val repository: RepositoryRoomDairyHealth = RepositoryRoomDairyHealth(DairyDatabase.invoke(application))
    var dairies: LiveData<List<Dairy>> = repository.getAll()

    fun insert(dairy: Dairy) {
        repository.insert(dairy)
    }

    fun update(dairy: Dairy){
        repository.update(dairy)
    }

    fun delete(dairy: Dairy){
        repository.delete(dairy)
    }

    fun getAll() = dairies;
}