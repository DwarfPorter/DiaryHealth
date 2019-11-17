package ru.vlmor.diaryhealth

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
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
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room
            .inMemoryDatabaseBuilder(context, DairyDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dairyDao = db.dairyDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.clearAllTables()
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertThenRead() {
        val dairy = Dairy()
        dairy.pressure.dia=100
        dairy.pressure.sys=42

        var id = dairyDao.insert(dairy)
        var dairyActual = dairyDao.get(id)
        assertEquals(dairy.pressure.sys, dairyActual.pressure.sys)
        dairyDao.delete(dairy)
    }

    @Test
    @Throws(Exception::class)
    fun insertThenReadAll() {
        val dairy = Dairy()
        dairy.pressure.dia=81
        dairy.pressure.sys=42

        var id = dairyDao.insert(dairy)
        var dairiesActual = dairyDao.getAll()
        assertEquals(1, dairiesActual.size)
        assertEquals(dairy.pressure.dia, dairiesActual.get(0).pressure.dia)
        dairyDao.delete(dairy)
    }
    @Test
    @Throws(Exception::class)
    fun insertThenUpdate() {
        val dairy = Dairy()
        dairy.pressure.dia=81
        dairy.pressure.sys=42

        var id = dairyDao.insert(dairy)
        var dairyForUpdate = dairyDao.get(id)
        dairyForUpdate.pressure.dia = 102
        dairyDao.update(dairyForUpdate)
        var dairyActual = dairyDao.get(id)
        assertEquals(dairyForUpdate.pressure.dia, dairyActual.pressure.dia)
        dairyDao.delete(dairy)
    }
}