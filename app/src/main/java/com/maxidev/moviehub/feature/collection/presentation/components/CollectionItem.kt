package com.maxidev.moviehub.feature.collection.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maxidev.moviehub.common.presentation.theme.MovieHubTheme
import com.maxidev.moviehub.feature.components.ImageItem
import kotlin.math.roundToInt

@Composable
fun CollectionItem(
    modifier: Modifier = Modifier,
    id: Int,
    title: String,
    posterPath: String,
    overview: String,
    releaseDate: String,
    voteAverage: Double,
    navigateToDetail: (Int) -> Unit
) {
    Box(
        modifier = modifier.padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        OutlinedCard(
            elevation = CardDefaults.cardElevation(6.dp),
            onClick = { navigateToDetail(id) }
        ) {
            Row(
                modifier = Modifier.padding(10.dp)
            ) {
                if (posterPath.isNotEmpty() || posterPath.isNotBlank()) {
                    ImageItem(
                        modifier = Modifier
                            .height(200.dp)
                            .aspectRatio(2f / 3f)
                            .clip(RoundedCornerShape(5))
                            .align(Alignment.CenterVertically),
                        imageUrl = posterPath,
                        contentScale = ContentScale.FillBounds,
                        navigateToDetail = { /* Do nothing. */ }
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = overview,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                    Text(
                        text = "Release date: ${releaseDate.ifEmpty { "TBA" }}\nVote average: ${voteAverage.roundToInt()} ⭐",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CollectionItemPreview() {
    MovieHubTheme {
        CollectionItem(
            id = 0,
            title = "Lorem ipsum",
            posterPath = "Poster",
            overview = "Lit orem ipsum dolor samet, consectetur adipiscing elit. " +
                    "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                    "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut " +
                    "aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
                    "voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
            releaseDate = "2025-01-01",
            voteAverage = 4.5,
            navigateToDetail = {}
        )
    }
}