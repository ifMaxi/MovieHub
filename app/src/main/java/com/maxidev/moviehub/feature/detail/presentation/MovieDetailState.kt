package com.maxidev.moviehub.feature.detail.presentation

import androidx.paging.PagingData
import com.maxidev.moviehub.feature.detail.domain.model.Casting
import com.maxidev.moviehub.feature.detail.domain.model.MovieDetail
import com.maxidev.moviehub.feature.detail.domain.model.MovieImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class MovieDetailState(
    val movieDetail: MovieDetail = MovieDetail(),
    val movieImages: Flow<PagingData<MovieImage>> = emptyFlow(),
    val movieCasting: Flow<PagingData<Casting>> = emptyFlow()
)