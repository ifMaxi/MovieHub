package com.maxidev.moviehub.feature.favorite.data.repository

import com.maxidev.moviehub.common.data.local.dao.MovieDao
import com.maxidev.moviehub.common.data.local.entity.MovieEntity
import com.maxidev.moviehub.feature.detail.domain.mapper.asEntity
import com.maxidev.moviehub.feature.detail.domain.mapper.asExternal
import com.maxidev.moviehub.feature.detail.domain.model.MovieDetail
import com.maxidev.moviehub.feature.favorite.domain.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val dao: MovieDao
): FavoriteRepository {

    override fun getAllFavorites(): Flow<List<MovieDetail>> =
        dao.getMovies().map { entity -> entity.map(MovieEntity::asExternal) }
            .flowOn(Dispatchers.IO)
            .distinctUntilChanged()
            .catch { emptyFlow<MovieDetail>() }

    override fun getFavoriteById(id: Int): Flow<MovieDetail> =
        dao.getMovieById(id).map(MovieEntity::asExternal)
            .flowOn(Dispatchers.IO)
            .distinctUntilChanged()
            .catch { emptyFlow<MovieDetail>() }

    override fun isFavorite(id: Int): Flow<Boolean> =
        dao.isFavorite(id)
            .flowOn(Dispatchers.IO)
            .catch { emptyFlow<Boolean>() }

    override suspend fun saveToFavorite(movie: MovieDetail) =
        withContext(Dispatchers.IO) {
            dao.saveMovie(movie.asEntity())
        }

    override suspend fun deleteFromFavorite(movie: MovieDetail) =
        withContext(Dispatchers.IO) {
            dao.deleteMovie(movie.asEntity())
        }

    override suspend fun clearAll() = withContext(Dispatchers.IO) { dao.clearAll() }
}