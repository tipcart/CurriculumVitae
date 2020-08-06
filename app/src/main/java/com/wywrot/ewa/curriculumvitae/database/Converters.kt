package com.wywrot.ewa.curriculumvitae.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

internal class Converters {
    @TypeConverter
    fun fromIntegerList(integers: List<Int?>?): String? =
        if (integers == null) null
        else Gson().toJson(integers, object : TypeToken<List<Int?>?>() {}.type)

    @TypeConverter
    fun toIntegerList(integersString: String?): List<Int?>? =
        if (integersString == null) null
        else Gson().fromJson(integersString, object : TypeToken<List<Int?>?>() {}.type)
}