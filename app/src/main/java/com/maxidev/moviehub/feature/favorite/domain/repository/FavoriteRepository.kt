package com.maxidev.moviehub.feature.favorite.domain.repository

import com.maxidev.moviehub.feature.detail.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    fun getAllFavorites(): Flow<List<MovieDetail>>

    fun getFavoriteById(id: Int): Flow<MovieDetail>

    fun isFavorite(id: Int): Flow<Boolean>

    suspend fun saveToFavorite(movie: MovieDetail)

    suspend fun deleteFromFavorite(movie: MovieDetail)

    suspend fun clearAll()
}