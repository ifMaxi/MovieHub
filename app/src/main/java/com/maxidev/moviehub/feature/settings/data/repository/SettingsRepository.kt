package com.maxidev.moviehub.feature.settings.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.maxidev.moviehub.feature.settings.data.utils.TypeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsRepository(context: Context) {

    private val dataStore = context.dataStore

    private object PreferencesKey {
        val DYNAMIC_THEME = booleanPreferencesKey("dynamic_theme")
        val THEME_TYPE = intPreferencesKey("theme_type")
    }

    val isDynamicThemeFlow : Flow<Boolean> =
        dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences()) else
                    throw exception
            }
            .map { preferences -> preferences[PreferencesKey.DYNAMIC_THEME] ?: false }

    val themeTypeFlow : Flow<TypeTheme> =
        dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences()) else
                    throw exception
            }
            .map { preferences ->
                TypeTheme.entries[preferences[PreferencesKey.THEME_TYPE] ?: 0]
            }

    fun setDynamicTheme(value: Boolean, scope: CoroutineScope) {
        scope.launch {
            dataStore.edit { preferences ->
                preferences[PreferencesKey.DYNAMIC_THEME] = value
            }
        }
    }

    fun setTypeTheme(value: TypeTheme, scope: CoroutineScope) {
        scope.launch {
            dataStore.edit { preferences ->
                preferences[PreferencesKey.THEME_TYPE] = value.ordinal
            }
        }
    }
}