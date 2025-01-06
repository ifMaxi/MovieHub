package com.maxidev.moviehub.feature.home.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.maxidev.moviehub.feature.home.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
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

    // Movies
    private fun getTrendingMovies() {
        Log.d("HomeViewModel", "getTrendingMovies: called")
        _homeState.update { trending ->
            trending.copy(
                trendingMovies = repository.fetchTrendingMovies()
                    .distinctUntilChanged()
                    .cachedIn(viewModelScope)
            )
        }
    }

    private fun getNowPlayingMovies() {
        Log.d("HomeViewModel", "getNowPlayingMovies: called")
        _homeState.update { nowPlaying ->
            nowPlaying.copy(
                nowPlayingMovies = repository.fetchNowPlayingMovies()
                    .distinctUntilChanged()
                    .cachedIn(viewModelScope)
            )
        }
    }

    private fun getPopularMovies() {
        Log.d("HomeViewModel", "getPopularMovies: called")
        _homeState.update { popular ->
            popular.copy(
                popularMovies = repository.fetchPopularMovies()
                    .distinctUntilChanged()
                    .cachedIn(viewModelScope)
            )
        }
    }

    private fun getUpcomingMovies() {
        Log.d("HomeViewModel", "getUpcomingMovies: called")
        _homeState.update { upcoming ->
            upcoming.copy(
                upcomingMovies = repository.fetchUpcomingMovies()
                    .distinctUntilChanged()
                    .cachedIn(viewModelScope)
            )
        }
    }

    private fun getTopRatedMovies() {
        Log.d("HomeViewModel", "getTopRatedMovies: called")
        _homeState.update { topRated ->
            topRated.copy(
                topRatedMovies = repository.fetchTopRatedMovies()
                    .distinctUntilChanged()
                    .cachedIn(viewModelScope)
            )
        }
    }

    // Pull to refresh
    fun refreshAll() {
        getTrendingMovies()
    }
}