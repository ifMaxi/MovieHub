package com.maxidev.moviehub.feature.detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.maxidev.moviehub.feature.detail.domain.model.MovieDetail
import com.maxidev.moviehub.feature.detail.domain.repository.MovieDetailRepository
import com.maxidev.moviehub.feature.favorite.domain.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieDetailRepository,
    private val favoriteRepository: FavoriteRepository
): ViewModel() {

    private val _detailState = MutableStateFlow(MovieDetailState())
    val detailState = _detailState.asStateFlow()

    fun fetchMovieDetail(movieId: Int) =
        viewModelScope.launch {
            _detailState.update { detail ->
                detail.copy(
                    movieDetail = repository.fetchMovieDetail(movieId)
                )
            }
        }

    fun fetchMovieImages(movieId: Int) =
        _detailState.update { images ->
            images.copy(
                movieImages = repository.fetchMovieImage(movieId)
                    .cachedIn(viewModelScope)
            )
        }

    fun fetchMovieCasting(movieId: Int) =
        _detailState.update { casting ->
            casting.copy(
                movieCasting = repository.fetchMovieCasting(movieId)
                    .cachedIn(viewModelScope)
            )
        }

    fun isFavorite(movieId: Int): Flow<Boolean> = favoriteRepository.isFavorite(movieId)

    fun saveFavorite(movie: MovieDetail) =
        viewModelScope.launch {
            favoriteRepository.saveToFavorite(movie)
        }

    fun deleteFavorite(movie: MovieDetail) =
        viewModelScope.launch {
            favoriteRepository.deleteFromFavorite(movie)
        }
}