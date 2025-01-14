package com.maxidev.moviehub.feature.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxidev.moviehub.feature.settings.data.repository.SettingsRepository
import com.maxidev.moviehub.feature.settings.data.utils.TypeTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository
): ViewModel() {

    val dialogVisible = MutableStateFlow(false)

    // Observe the Datastore preferences
    val isDynamic: StateFlow<Boolean> =
        repository.isDynamicThemeFlow.map { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = true
            )

    val isTypeTheme: StateFlow<SettingsState> =
        repository.themeTypeFlow.map { SettingsState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = SettingsState(TypeTheme.SYSTEM)
            )

    fun setDialogVisible(value: Boolean) {
        dialogVisible.value = value
    }

    fun updateIsDynamicTheme() {
        repository.setDynamicTheme(value = !isDynamic.value, scope = viewModelScope)
    }

    fun updateIsDarkTheme(value: TypeTheme) {
        repository.setTypeTheme(value = value, scope = viewModelScope)
    }
}