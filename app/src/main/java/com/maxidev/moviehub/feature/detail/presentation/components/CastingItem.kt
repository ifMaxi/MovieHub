package com.maxidev.moviehub.feature.detail.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.SubcomposeAsyncImage
import com.maxidev.moviehub.R
import com.maxidev.moviehub.common.presentation.theme.MovieHubTheme
import com.maxidev.moviehub.feature.components.RowLazyItem
import com.maxidev.moviehub.feature.detail.domain.model.Casting

@Composable
fun CastingContent(
    modifier: Modifier = Modifier,
    cast: List<Casting>
) {
    Box(
        modifier = modifier.padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = stringResource(R.string.cast),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.Start)
            )
            RowLazyItem(
                items = cast,
                key = { key -> key.id },
                content = { casting ->
                    CastingItem(
                        name = casting.name,
                        profilePath = casting.profilePath,
                        character = casting.character
                    )
                }
            )
        }
    }
}

@Composable
private fun CastingItem(
    modifier: Modifier = Modifier,
    name: String,
    profilePath: String,
    character: String
) {
    val asyncImageLink = "https://image.tmdb.org/t/p/original$profilePath"

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        OutlinedCard(elevation = CardDefaults.outlinedCardElevation(6.dp)) {
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .aspectRatio(2f)
                        .size(120.dp)
                        .clip(CircleShape),
                    model = asyncImageLink,
                    contentDescription = name,
                    contentScale = ContentScale.Crop,
                    error = {
                        if (profilePath.isEmpty()) {
                            Image(
                                painter = painterResource(R.drawable.user_286_512),
                                contentDescription = null
                            )
                        }
                    }
                )
                Text(
                    text = name,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(6.dp)
                )
                Text(
                    text = character,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(6.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun CastingItemPreview() {
    MovieHubTheme {
        CastingItem(
            name = "Tom Holland",
            profilePath = "image",
            character = "Peter Parker"
        )
    }
}