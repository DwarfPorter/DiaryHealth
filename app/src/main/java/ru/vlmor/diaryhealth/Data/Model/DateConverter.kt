package ru.vlmor.diaryhealth.data.model

import androidx.room.TypeConverter
import java.util.*

class DateConverter{
    @TypeConverter fun fromTypestamp(value: Long): Date {
        return Date(value);
    }

    @TypeConverter fun fromDate(value: Date): Long {
        return value.time;
    }
}