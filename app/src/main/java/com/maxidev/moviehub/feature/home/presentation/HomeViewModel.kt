package com.maxidev.moviehub.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.maxidev.moviehub.feature.home.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
): ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    init {
        getTrendingMovies()
        getNowPlayingMovies()
        getTopRatedMovies()
        getPopularMovies()
        getUpcomingMovies()
    }

    private fun getTrendingMovies() {
        _homeState.update { trending ->
            trending.copy(
                trendingMovies = repository.fetchTrendingMovies()
                    .cachedIn(viewModelScope)
            )
        }
    }

    private fun getNowPlayingMovies() {
        _homeState.update { nowPlaying ->
            nowPlaying.copy(
                nowPlayingMovies = repository.fetchNowPlayingMovies()
                    .cachedIn(viewModelScope)
            )
        }
    }

    private fun getPopularMovies() {
        _homeState.update { popular ->
            popular.copy(
                popularMovies = repository.fetchPopularMovies()
                    .cachedIn(viewModelScope)
            )
        }
    }

    private fun getUpcomingMovies() {
        _homeState.update { upcoming ->
            upcoming.copy(
                upcomingMovies = repository.fetchUpcomingMovies()
                    .cachedIn(viewModelScope)
            )
        }
    }

    private fun getTopRatedMovies() {
        _homeState.update { topRated ->
            topRated.copy(
                topRatedMovies = repository.fetchTopRatedMovies()
                    .cachedIn(viewModelScope)
            )
        }
    }

    // Pull to refresh
    fun refreshAll() {
        getTrendingMovies()
    }
}