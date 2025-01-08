package com.maxidev.moviehub.feature.detail.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.maxidev.moviehub.R
import com.maxidev.moviehub.feature.components.ImageItem
import com.maxidev.moviehub.feature.components.PagedRow
import com.maxidev.moviehub.feature.detail.domain.model.MovieImage

@Composable
fun BackgroundImagesItem(
    modifier: Modifier = Modifier,
    images: LazyPagingItems<MovieImage>
) {
    Box(
        modifier = modifier.padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.background_images),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.Start)
            )
            PagedRow(
                items = images,
                key = { key -> key.backdrops },
                content = {
                    ImageItem(
                        modifier = Modifier
                            .aspectRatio(2f / 3f)
                            .size(width = 320.dp, height = 180.dp),
                        imageUrl = it.backdrops,
                        contentScale = ContentScale.FillBounds,
                        navigateToDetail = {}
                    )
                }
            )
        }
    }
}