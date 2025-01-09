package com.maxidev.moviehub.feature.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

/**
 * A composable function that displays an image within a card.
 *
 * This function fetches and displays an image from a given URL using Coil's AsyncImage.
 * The image is displayed within a Material Design Card, with a rounded corner shape and elevation.
 * Clicking on the image triggers a navigation action.
 *
 * @param modifier Modifier for styling and layout adjustments of the image container.
 * @param imageUrl The path of the image URL (appended to the base url: "https://image.tmdb.org/t/p/original") to be displayed.
 *                 For example, if you provide "/path/to/image.jpg" it will download from "https://image.tmdb.org/t/p/original/path/to/image.jpg"
 * @param contentScale How the image should be scaled to fit its bounds. See [ContentScale] for available options.
 * @param navigateToDetail A lambda function that is invoked when the image is clicked.
 *                         This should typically be used to navigate to a detail screen related to the image.
 */
@Composable
fun ImageItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentScale: ContentScale,
    navigateToDetail: () -> Unit
) {
    val baseImageUrl = "https://image.tmdb.org/t/p/original$imageUrl"

    Card(
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(4)
    ) {
        AsyncImage(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4))
                .clickable { navigateToDetail() },
            model = baseImageUrl,
            contentDescription = null,
            contentScale = contentScale
        )
    }
}