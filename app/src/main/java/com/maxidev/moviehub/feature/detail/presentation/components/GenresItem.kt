package com.maxidev.moviehub.feature.detail.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maxidev.moviehub.common.presentation.theme.MovieHubTheme
import com.maxidev.moviehub.common.presentation.theme.nunitoFont

/**
 * Displays a horizontal list of genre tags within a FlowRow layout.
 *
 * This composable function takes a list of genre strings and displays each
 * genre as a circular card with the genre name enclosed in double quotes.
 * The genres are arranged in a flowing layout that wraps to the next line
 * if the available width is exceeded.
 *
 * @param modifier The modifier to be applied to the layout.
 * @param genres A list of strings representing the genres to display.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenresItem(
    modifier: Modifier = Modifier,
    genres: List<String>
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        FlowRow(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            genres.forEach { genre ->
                Card(
                    elevation = CardDefaults.cardElevation(6.dp),
                    shape = CircleShape
                ) {
                    Text(
                        text = "\"$genre\"",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = nunitoFont,
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 8.dp)
                            .semantics { contentDescription = genre }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun GenresItemPreview() {
    MovieHubTheme {
        GenresItem(genres = listOf("Action", "Adventure", "Fantasy"))
    }
}