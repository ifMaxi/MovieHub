package com.maxidev.moviehub.feature.favorite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxidev.moviehub.feature.favorite.domain.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: FavoriteRepository
): ViewModel() {

    val favoriteState: StateFlow<FavoriteState> =
        repository.getAllFavorites().map { FavoriteState(favorites = it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = FavoriteState()
            )

//    fun favoriteById(id: Int) =
//        viewModelScope.launch { repository.getFavoriteById(id) }

    fun deleteAllFavorites() =
        viewModelScope.launch { repository.clearAll() }
}