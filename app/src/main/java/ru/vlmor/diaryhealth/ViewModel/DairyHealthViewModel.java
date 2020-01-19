package ru.vlmor.diaryhealth.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.vlmor.diaryhealth.data.database.DairyDatabase;
import ru.vlmor.diaryhealth.data.model.Dairy;
import ru.vlmor.diaryhealth.repository.RepositoryDairyHealth;
import ru.vlmor.diaryhealth.repository.RepositoryRoomDairyHealth;

public class DairyHealthViewModel extends AndroidViewModel {

    private RepositoryDairyHealth repositoryDairyHealth;
    private LiveData<List<Dairy>> dairies;

    public DairyHealthViewModel(@NonNull Application application) {
        super(application);
        repositoryDairyHealth = new RepositoryRoomDairyHealth(DairyDatabase.Companion.invoke(application));
        dairies = repositoryDairyHealth.getAll();
    }

    public void insert(Dairy dairy){
        repositoryDairyHealth.insert(dairy);
    }

    public void update(Dairy dairy){
        repositoryDairyHealth.update(dairy);
    }

    public void delete(Dairy dairy){
        repositoryDairyHealth.delete(dairy);
    }

    public LiveData<List<Dairy>> getAllDairies(){
        return dairies;
    }
}
