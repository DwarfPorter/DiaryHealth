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

@Database(entities = [Dairy::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class DairyDatabase: RoomDatabase() {
    abstract fun dairyDao(): DairyHealthDao
}