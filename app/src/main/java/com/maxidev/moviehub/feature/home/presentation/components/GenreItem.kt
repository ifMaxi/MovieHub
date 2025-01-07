package com.maxidev.moviehub.feature.home.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maxidev.moviehub.common.presentation.theme.MovieHubTheme

@Composable
fun GenreItem(
    modifier: Modifier = Modifier,
    name: String
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Card(elevation = CardDefaults.cardElevation(6.dp)) {
            Text(
                text = name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 2.dp)
            )
        }
    }
}

@Preview
@Composable
private fun GenreItemPreview() {
    MovieHubTheme {
        GenreItem(name = "Fiction")
    }
}