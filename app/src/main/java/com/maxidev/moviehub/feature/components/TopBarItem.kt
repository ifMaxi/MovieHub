package com.maxidev.moviehub.feature.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.maxidev.moviehub.R
import com.maxidev.moviehub.common.presentation.theme.dmSansFont

/**
 * A composable function that creates a centered top app bar with customizable title,
 * navigation icon, actions, and scroll behavior.
 *
 * @param modifier The modifier to be applied to the top app bar.
 * @param title The string resource ID for the title of the top app bar.
 *              If null, an empty string resource (R.string.empty_resource) will be used as fallback.
 * @param navigationIcon A composable function that provides the navigation icon for the top app bar.
 *                       This is typically a back button or a menu icon.
 * @param actions A composable function that defines the actions for the top app bar.
 *                This is typically a row of icons or text buttons.
 * @param scrollBehavior The scroll behavior for the top app bar. This allows the top app bar
 *                       to react to scrolling events, such as collapsing or expanding.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarItem(
    modifier: Modifier = Modifier,
    @StringRes title: Int?,
    navigationIcon: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(title ?: R.string.empty_resource),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = dmSansFont
            )
        },
        actions = actions,
        navigationIcon = navigationIcon,
        scrollBehavior = scrollBehavior
    )
}