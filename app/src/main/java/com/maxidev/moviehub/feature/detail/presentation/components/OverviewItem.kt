package com.maxidev.moviehub.feature.detail.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maxidev.moviehub.R
import com.maxidev.moviehub.common.presentation.theme.MovieHubTheme
import com.maxidev.moviehub.common.presentation.theme.dmSansFont
import com.maxidev.moviehub.common.presentation.theme.nunitoFont

/**
 * Composable function to display an overview item with a title and description.
 *
 * This function renders a column containing a title "Overview" and a provided overview text.
 * It's designed to be used within a larger layout to display a summary or description of something.
 *
 * @param modifier The modifier to be applied to the column. Allows customization of layout properties like padding, size, etc.
 * @param overview The text content of the overview to be displayed.
 *
 */
@Composable
fun OverviewItem(
    modifier: Modifier = Modifier,
    overview: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.overview),
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = dmSansFont,
            modifier = Modifier.align(Alignment.Start)
        )
        Text(
            text = overview,
            fontSize = 14.sp,
            fontFamily = nunitoFont,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun OverviewItemPreview() {
    MovieHubTheme {
        OverviewItem(
            overview = "Lit orem ipsum dolor samet, consectetur adipiscing elit. " +
                    "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                    "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut " +
                    "aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
                    "voluptate velit esse cillum dolore eu fugiat nulla pariatur."
        )
    }
}