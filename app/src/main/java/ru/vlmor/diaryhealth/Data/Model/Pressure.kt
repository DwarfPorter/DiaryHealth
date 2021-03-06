package ru.vlmor.diaryhealth.data.model

import androidx.room.ColumnInfo

data class Pressure(
    @ColumnInfo(name = "PressureSys") var sys: Int,
    @ColumnInfo(name = "PressureDia") var dia: Int,
    @ColumnInfo(name = "PressurePulse")var pulse: Int
)