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

@Composable
fun ImageItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentScale: ContentScale,
    navigateToDetail: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(4)
    ) {
        AsyncImage(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4))
                .clickable { navigateToDetail() },
            model = "https://image.tmdb.org/t/p/original$imageUrl",
            contentDescription = null,
            contentScale = contentScale
        )
    }
}