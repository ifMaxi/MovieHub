package com.maxidev.moviehub.feature.collection.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxidev.moviehub.feature.collection.domain.repository.CollectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val repository: CollectionRepository
): ViewModel() {

    private val _collectionState = MutableStateFlow(CollectionsState())
    val collectionState = _collectionState.asStateFlow()

    fun fetchCollection(collectionId: Int) =
        viewModelScope.launch {
            _collectionState.update { collection ->
                collection.copy(
                    collections = repository.fetchCollection(collectionId)
                )
            }
        }
}