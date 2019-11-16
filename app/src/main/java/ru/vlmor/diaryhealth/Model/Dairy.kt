package ru.vlmor.diaryhealth.Model

import androidx.room.*
import java.util.*

@Entity(tableName = "Dairy", indices = [Index(value = ["Date"])])
data class Dairy(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @Embedded var pressure: Pressure,
    @ColumnInfo(name = "Date") var date: Date
)