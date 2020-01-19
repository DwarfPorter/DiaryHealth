package ru.vlmor.diaryhealth

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import ru.vlmor.diaryhealth.data.database.DairyDatabase
import ru.vlmor.diaryhealth.data.model.Dairy
import ru.vlmor.diaryhealth.repository.RepositoryRoomDairyHealth
import java.util.*


class PopulatorTestData (context: Context):LifecycleObserver {
    private val context: Context = context

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart(){
        val repository = RepositoryRoomDairyHealth(DairyDatabase.invoke(context))
        val dairy1 = createDairy(120, 70, 2020, 1,1)
        repository.insert(dairy1)
        val dairy2 = createDairy(130, 80, 2020, 1, 2)
        repository.insert(dairy2)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onDestroy(){
        val repository = RepositoryRoomDairyHealth(DairyDatabase.invoke(context))
        repository.deleteAll()
    }

    private fun createDairy(dia: Int, sys: Int, year: Int, month: Int, day: Int):Dairy{
        val dairy1 = Dairy()
        dairy1.pressure.dia = 120
        dairy1.pressure.sys = 70
        val myCal = Calendar.getInstance()
        myCal[Calendar.YEAR] = 2020
        myCal[Calendar.MONTH] = 1
        myCal[Calendar.DAY_OF_MONTH] = 1
        dairy1.date = myCal.time
        return dairy1
    }
}