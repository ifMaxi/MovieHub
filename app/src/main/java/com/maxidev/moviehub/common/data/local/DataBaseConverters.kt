package com.maxidev.moviehub.common.data.local

import androidx.room.TypeConverter

class DataBaseConverters {

    @TypeConverter
    fun stringToList(value: String): List<String> = value.split(",").map { str ->
        str.trim()
    }

    @TypeConverter
    fun listToString(value: List<String>): String = value.joinToString(separator = ",")
}