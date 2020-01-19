package ru.vlmor.diaryhealth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.vlmor.diaryhealth.data.database.DairyDatabase
import ru.vlmor.diaryhealth.data.model.Dairy
import ru.vlmor.diaryhealth.repository.RepositoryRoomDairyHealth
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(AndroidJUnit4::class)
class RepositoryRoomDairyHealthTest{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var repositoryRoomDairyHealth : RepositoryRoomDairyHealth

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val db = Room
            .inMemoryDatabaseBuilder(context, DairyDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        repositoryRoomDairyHealth = RepositoryRoomDairyHealth(db);
    }

    @Test
    @Throws(Exception::class)
    fun insertThenRead_Update_ReadAll_Delete() {
        val dairy = Dairy()
        dairy.pressure.dia = 100
        dairy.pressure.sys = 42

        var id = repositoryRoomDairyHealth.insert(dairy)
        var dairyRead = repositoryRoomDairyHealth.get(id).getOrAwaitValue()
        Assert.assertEquals(42, dairyRead.pressure.sys)

        dairyRead.pressure.sys = 81;
        var dairyUpdated = repositoryRoomDairyHealth.update(dairyRead).getOrAwaitValue()
        Assert.assertEquals(81, dairyUpdated.pressure.sys)

        var dairiesReadAll = repositoryRoomDairyHealth.getAll().getOrAwaitValue()
        Assert.assertEquals(1, dairiesReadAll.size)
        Assert.assertEquals(81, dairiesReadAll.get(0).pressure.sys)

        repositoryRoomDairyHealth.delete(dairyUpdated)
        var dairiesReadAllAfterDelete = repositoryRoomDairyHealth.getAll().getOrAwaitValue()
        Assert.assertEquals(0, dairiesReadAllAfterDelete.size)
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
}