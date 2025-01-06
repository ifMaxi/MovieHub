package com.maxidev.moviehub.common.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun formatDateUtils(input: String): String {

    val inputFormat = SimpleDateFormat("yyyy-MM-dd")
    val outputFormat = SimpleDateFormat("yyyy/MM/dd")

    val date = inputFormat.parse(input)

    return date?.let { outputFormat.format(it) }.toString()
}