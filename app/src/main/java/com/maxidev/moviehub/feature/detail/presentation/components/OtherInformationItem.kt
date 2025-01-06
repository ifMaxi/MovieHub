package com.maxidev.moviehub.feature.detail.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maxidev.moviehub.common.presentation.theme.MovieHubTheme
import com.maxidev.moviehub.common.utils.formatDateUtils
import kotlin.math.roundToInt

@Composable
fun OtherInformationItem(
    modifier: Modifier = Modifier,
    status: String,
    releaseDate: String,
    runtime: Int,
    voteAverage: Double
) {
    val formatDate = formatDateUtils(releaseDate)
    val textStyle = TextStyle.Default.copy(
        fontSize = 14.sp,
        fontWeight = FontWeight.Light,
        textAlign = TextAlign.Start,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )

    Box(
        modifier = modifier.wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Other information",
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "• Release date: $formatDate",
                style = textStyle
            )
            Text(
                text = "• Duration: $runtime min",
                style = textStyle
            )
            Text(
                text = "• Status: $status",
                style = textStyle
            )
            Text(
                text = "• Rating: ${voteAverage.roundToInt()} ⭐",
                style = textStyle
            )
        }
    }
}

@Preview
@Composable
private fun OtherInformationItemPreview() {
    MovieHubTheme {
        OtherInformationItem(
            status = "Released",
            releaseDate = "2023-01-01",
            runtime = 120,
            voteAverage = 8.5
        )
    }
}