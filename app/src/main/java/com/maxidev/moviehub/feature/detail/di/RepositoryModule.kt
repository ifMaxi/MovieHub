package com.maxidev.moviehub.feature.detail.di

import com.maxidev.moviehub.feature.detail.data.repository.MovieDetailRepositoryImpl
import com.maxidev.moviehub.feature.detail.domain.repository.MovieDetailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsMovieDetailRepository(impl: MovieDetailRepositoryImpl): MovieDetailRepository
}