package com.maxidev.moviehub.feature.settings.presentation

import com.maxidev.moviehub.feature.settings.data.utils.TypeTheme

data class SettingsState(
    val selectedRadio: TypeTheme,
    val radioItems: List<RadioItem> = listOf(
        RadioItem(value = TypeTheme.SYSTEM, title = "System"),
        RadioItem(value = TypeTheme.DARK, title = "Dark"),
        RadioItem(value = TypeTheme.LIGHT, title = "Light")
    )
)

data class RadioItem(val value: TypeTheme, val title: String)