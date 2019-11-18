package ru.vlmor.diaryhealth.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.vlmor.diaryhealth.Dao.DairyHealthDao
import ru.vlmor.diaryhealth.Model.Dairy
import ru.vlmor.diaryhealth.Model.DateConverter

//https://medium.com/mindorks/android-architecture-components-room-and-kotlin-f7b725c8d1d
//https://developer.android.com/training/data-storage/room/testing-db
//https://gabrieltanner.org/blog/android-room
//https://medium.com/@nyavorskii/%D0%B7%D0%BD%D0%B0%D0%BA%D0%BE%D0%BC%D1%81%D1%82%D0%B2%D0%BE-%D1%81-android-architecture-components-%D0%B8-mvvm-%D0%BF%D0%B5%D1%80%D0%B5%D0%B2%D0%BE%D0%B4-29654672f4ab

@Database(entities = [Dairy::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class DairyDatabase: RoomDatabase() {
    abstract fun dairyDao(): DairyHealthDao

    companion object {
        @Volatile private var instance: DairyDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            DairyDatabase::class.java, "dairy_health.db")
            .build()
    }
}