package com.maxidev.moviehub.feature.detail.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maxidev.moviehub.R
import com.maxidev.moviehub.common.presentation.theme.MovieHubTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductionCompaniesItem(
    modifier: Modifier = Modifier,
    companies: List<String>
) {
    Box(
        modifier = modifier.wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        FlowColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = stringResource(R.string.production_companies),
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium
            )

            companies.forEach { item ->
                Text(
                    text = "• $item",
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProductionCompaniesItemPreview() {
    MovieHubTheme {
        ProductionCompaniesItem(companies = listOf("Disney", "Pixar", "Marvel"))
    }
}