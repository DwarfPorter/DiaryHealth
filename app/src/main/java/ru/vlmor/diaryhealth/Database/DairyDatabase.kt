package ru.vlmor.diaryhealth.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.vlmor.diaryhealth.Dao.DairyHealthDao
import ru.vlmor.diaryhealth.Model.Dairy
import ru.vlmor.diaryhealth.Model.DateConverter

@Database(entities = [Dairy::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class DairyDatabase: RoomDatabase(), DairyHealthDao {
    abstract fun dairyDao(): DairyHealthDao

    companion object{
        private var instance: DairyDatabase? = null

        fun getInstance(context: Context): DairyDatabase {
            if (instance == null) {
                synchronized(DairyDatabase::class) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        DairyDatabase::class.java, "dairy_health")
                        .build()
                }
            }
            return instance!!
        }

        fun destroyInstance() {
            instance?.close()
            instance = null
        }
    }
}