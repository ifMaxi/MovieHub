package com.maxidev.moviehub.common.presentation.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxidev.moviehub.feature.settings.data.repository.SettingsRepository
import com.maxidev.moviehub.feature.settings.data.utils.TypeTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    repository: SettingsRepository
): ViewModel() {

    // Observe the Datastore preferences
    val isDynamic: StateFlow<Boolean> =
        repository.isDynamicThemeFlow.map { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = true
            )

    val isDarkTheme: StateFlow<TypeTheme> =
        repository.themeTypeFlow.map { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = TypeTheme.SYSTEM
            )
}