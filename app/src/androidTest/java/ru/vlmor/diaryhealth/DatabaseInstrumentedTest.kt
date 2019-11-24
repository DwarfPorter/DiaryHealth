package ru.vlmor.diaryhealth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import ru.vlmor.diaryhealth.data.dao.DairyHealthDao
import ru.vlmor.diaryhealth.data.database.DairyDatabase
import ru.vlmor.diaryhealth.data.model.Dairy
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(AndroidJUnit4::class)
class DatabaseInstrumentedTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

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
        dairy.pressure.dia = 100
        dairy.pressure.sys = 42

        var id = dairyDao.insert(dairy)
        var dairyActual = dairyDao.get(id).getOrAwaitValue()

        assertEquals(42, dairyActual.pressure.sys)
        dairyDao.delete(dairy)
    }

    @Test
    @Throws(Exception::class)
    fun insertThenReadAll() {
        val dairy = Dairy()
        dairy.pressure.dia = 81
        dairy.pressure.sys = 42

        var id = dairyDao.insert(dairy)
        var dairiesActual = dairyDao.getAll().getOrAwaitValue()
        assertEquals(1, dairiesActual.size)
        assertEquals(81, dairiesActual.get(0).pressure.dia)
        dairyDao.delete(dairy)
    }

    @Test
    @Throws(Exception::class)
    fun insertThenUpdate() {
        val dairy = Dairy()
        dairy.pressure.dia = 81
        dairy.pressure.sys = 42

        var id = dairyDao.insert(dairy)
        var dairyForUpdate = dairyDao.get(id).getOrAwaitValue()
        dairyForUpdate.pressure.dia = 102
        dairyDao.update(dairyForUpdate)
        var dairyActual = dairyDao.get(id).getOrAwaitValue()
        assertEquals(102, dairyActual.pressure.dia)
        dairyDao.delete(dairy)
    }


    //https://stackoverflow.com/questions/44270688/unit-testing-room-and-livedata
    fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T?) {
                data = o
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }

        this.observeForever(observer)

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

        @Suppress("UNCHECKED_CAST")
        return data as T
    }

    private fun <T> LiveData<T>.blockingObserve(): T? {
        var value: T? = null
        val latch = CountDownLatch(1)

        val observer = Observer<T> { t ->
            value = t
            latch.countDown()
        }

        observeForever(observer)

        latch.await(2, TimeUnit.SECONDS)
        return value
    }
}