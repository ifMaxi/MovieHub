package com.maxidev.moviehub.feature.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.maxidev.moviehub.common.presentation.theme.dmSansFont

@Composable
fun HeaderItem(
    modifier: Modifier = Modifier,
    @StringRes header: Int
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(header),
            fontWeight = FontWeight.Medium,
            fontSize = 22.sp,
            fontFamily = dmSansFont
        )
    }
}