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
            shape = RoundedCornerShape(4),
            shadowElevation = 6.dp
        ) {
            /* Do nothing. */
        }
    }
}