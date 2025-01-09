package com.maxidev.moviehub.feature.detail.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.maxidev.moviehub.common.presentation.theme.MovieHubTheme
import com.maxidev.moviehub.common.presentation.theme.dmSansFont
import com.maxidev.moviehub.common.presentation.theme.nunitoFont

/**
 * A composable function that displays a poster image with a title and an optional tagline.
 *
 * The poster image is loaded from a URL constructed using the `backdropPath`.
 * A gradient overlay is applied to the image for visual effect.
 * The title is displayed below the image, and the tagline is displayed below the title if provided.
 *
 * @param modifier The modifier to be applied to the root Box.
 * @param backdropPath The path to the backdrop image on the TMDB API. This will be appended to the base URL to fetch the image.
 *                     Example: "/path/to/image.jpg".
 * @param title The title to display below the image.
 * @param tagline An optional tagline to display below the title. If empty or blank, the tagline won't be shown.
 */
@Composable
fun PosterWithTextItem(
    modifier: Modifier = Modifier,
    backdropPath: String,
    title: String,
    tagline: String
) {
    val backdropUrl = "https://image.tmdb.org/t/p/original$backdropPath"

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (backdropPath.isNotBlank() || backdropPath.isNotEmpty()) {
                AsyncImage(
                    model = backdropUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .aspectRatio(20f / 14f)
                        .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
                        .drawWithContent {
                            val colors = listOf(
                                Color.Black,
                                Color.Black,
                                Color.Transparent,
                                Color.Transparent
                            )
                            drawContent()
                            drawRect(
                                brush = Brush.verticalGradient(
                                    colors = colors,
                                    startY = 0f,
                                    endY = 1000f
                                ),
                                blendMode = BlendMode.DstIn
                            )
                        }
                )
            }
            Text(
                text = title,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = dmSansFont,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .semantics { contentDescription = title }
            )
            if (tagline.isNotBlank() && tagline.isNotEmpty()) {
                Text(
                    text = "$tagline-",
                    fontSize = 12.sp,
                    fontFamily = nunitoFont,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier
                        .semantics { contentDescription = tagline }
                )
            }
        }
    }
}

@Preview
@Composable
private fun PosterWithTextPreview() {
    MovieHubTheme {
        PosterWithTextItem(
            backdropPath = "Image",
            title = "Lorem impsum",
            tagline = "Lorem impsum dolor sit amet."
        )
    }
}