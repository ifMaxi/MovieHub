package com.maxidev.moviehub.common.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.maxidev.moviehub.common.data.local.dao.MovieDao
import com.maxidev.moviehub.common.data.local.entity.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DataBaseConverters::class)
abstract class AppDataBase: RoomDatabase() {

    abstract fun movieDao(): MovieDao
}