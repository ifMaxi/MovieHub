package com.maxidev.moviehub.common.di

import android.content.Context
import androidx.room.Room
import com.maxidev.moviehub.common.data.local.AppDataBase
import com.maxidev.moviehub.common.data.local.dao.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    private const val DATA_BASE_NAME = "movie_hub_database"

    @Provides
    @Singleton
    fun providesRoomDataBase(
        @ApplicationContext context: Context
    ): AppDataBase {

        return Room.databaseBuilder(
            context = context,
            klass = AppDataBase::class.java,
            name = DATA_BASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providesMovieDao(db: AppDataBase): MovieDao = db.movieDao()
}