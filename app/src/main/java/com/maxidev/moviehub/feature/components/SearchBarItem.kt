package com.maxidev.moviehub.feature.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * A composable function that represents a search bar item.
 *
 * This function provides a customizable search bar that allows users to input text,
 * expand/collapse the search area, and trigger a search action. It utilizes Material 3's
 * `SearchBar` and `SearchBarDefaults.InputField` components for its core functionality.
 *
 * @param modifier The modifier to be applied to the search bar.
 * @param input The current text input in the search bar.
 * @param isExpanded True if the search area is expanded, false otherwise.
 * @param onInputChange Callback invoked when the input text changes. Provides the new input string.
 * @param onExpandedChange Callback invoked when the expanded state changes. Provides the new expanded state (true/false).
 * @param onSearch Callback invoked when the user triggers a search action (e.g., presses the enter key).
 *                 Provides the current input string.
 * @param placeholder A composable that represents the placeholder text to be displayed when the input is empty.
 * @param leadingIcon A composable that represents the icon displayed at the beginning of the input field.
 * @param trailingIcon A composable that represents the icon displayed at the end of the input field.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarItem(
    modifier: Modifier = Modifier,
    input: String,
    isExpanded: Boolean,
    onInputChange: (String) -> Unit,
    onExpandedChange: (Boolean) -> Unit,
    onSearch: (String) -> Unit,
    placeholder: @Composable (() -> Unit),
    leadingIcon: @Composable (() -> Unit),
    trailingIcon: @Composable (() -> Unit)
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Top),
        contentAlignment = Alignment.Center
    ) {
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = input,
                    onQueryChange = onInputChange,
                    onSearch = onSearch,
                    expanded = isExpanded,
                    onExpandedChange = onExpandedChange,
                    placeholder = placeholder,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon
                )
            },
            expanded = isExpanded,
            onExpandedChange = onExpandedChange,
            shape = RoundedCornerShape(5),
            shadowElevation = 6.dp
        ) {
            /* Do nothing. */
        }
    }
}