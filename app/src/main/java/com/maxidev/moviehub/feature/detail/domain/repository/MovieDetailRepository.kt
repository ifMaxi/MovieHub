package com.maxidev.moviehub.feature.detail.domain.repository

import androidx.paging.PagingData
import com.maxidev.moviehub.feature.detail.domain.model.Casting
import com.maxidev.moviehub.feature.detail.domain.model.MovieDetail
import com.maxidev.moviehub.feature.detail.domain.model.MovieImage
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {

    suspend fun fetchMovieDetail(movieId: Int): MovieDetail

    fun fetchMovieImage(movieId: Int): Flow<PagingData<MovieImage>>

    fun fetchMovieCasting(movieId: Int): Flow<PagingData<Casting>>
}