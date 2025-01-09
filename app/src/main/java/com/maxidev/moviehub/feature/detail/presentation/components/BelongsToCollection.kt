package com.maxidev.moviehub.feature.detail.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maxidev.moviehub.R
import com.maxidev.moviehub.common.presentation.theme.MovieHubTheme
import com.maxidev.moviehub.common.presentation.theme.dmSansFont
import com.maxidev.moviehub.common.presentation.theme.nunitoFont
import com.maxidev.moviehub.feature.components.ImageItem

/**
 * Displays an item that belongs to a movie or TV show collection.
 *
 * This composable displays a card that represents a single item within a collection.
 * It shows the item's poster image, name, and a label indicating it's part of a collection.
 * Clicking on the poster navigates to the detail screen of the collection.
 *
 * @param modifier The modifier to apply to the layout.
 * @param id The unique identifier of the collection.
 * @param name The name of the collection.
 * @param posterPath The URL or path to the poster image of the collection.
 * @param collectionId A lambda function that is invoked when the user clicks on the poster.
 *                     It passes the collection's `id` to handle navigation to its detail screen.
 */
@Composable
fun BelongsToCollectionItem(
    modifier: Modifier = Modifier,
    id: Int,
    name: String,
    posterPath: String,
    collectionId: (Int) -> Unit
) {
    if (posterPath.isNotEmpty() || posterPath.isNotBlank()) {
        Box(
            modifier = modifier
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.part_of_the_collection),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = dmSansFont,
                    modifier = Modifier.align(Alignment.Start)
                )
                OutlinedCard(elevation = CardDefaults.cardElevation(6.dp)) {
                    ImageItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .aspectRatio(2f / 3f),
                        imageUrl = posterPath,
                        contentScale = ContentScale.Crop,
                        navigateToDetail = { collectionId(id) }
                    )

                    Text(
                        text = name,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle.Default.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = nunitoFont,
                            lineHeight = 24.sp,
                            letterSpacing = 0.5.sp,
                            shadow = Shadow(
                                color = Color.White,
                                blurRadius = 3f
                            )
                        ),
                        modifier = Modifier
                            .padding(10.dp)
                            .semantics { contentDescription = name }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun BelongsToCollectionItemPreview() {
    MovieHubTheme {
        BelongsToCollectionItem(
            id = 0,
            name =  "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            posterPath = "Image",
            collectionId = {}
        )
    }
}