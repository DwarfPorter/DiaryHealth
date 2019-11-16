package ru.vlmor.diaryhealth

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import ru.vlmor.diaryhealth.Dao.DairyHealthDao
import ru.vlmor.diaryhealth.Database.DairyDatabase
import ru.vlmor.diaryhealth.Model.Dairy
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseInstrumentedTest{

    private lateinit var dairyDao: DairyHealthDao
    private lateinit var db: DairyDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = DairyDatabase.getInstance(context)
        dairyDao = db.dairyDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        DairyDatabase.destroyInstance()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        val dairy = Dairy()
        dairy.pressure.dia=100
        dairy.pressure.sys=42

        var id = dairyDao.insert(dairy)
        var dairyActual = dairyDao.get(id)
        assertEquals(dairy.pressure.sys, dairyActual.pressure.sys)
    }

}