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
import com.maxidev.moviehub.feature.components.ImageItem

@Composable
fun BelongsToCollectionItem(
    modifier: Modifier = Modifier,
    name: String,
    posterPath: String
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
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.align(Alignment.Start)
                )
                OutlinedCard(
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    ImageItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .aspectRatio(2f / 3f),
                        imageUrl = posterPath,
                        contentScale = ContentScale.Crop,
                        navigateToDetail = {}
                    )

                    Text(
                        text = name,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle.Default.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
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
            name =  "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            posterPath = "Image"
        )
    }
}