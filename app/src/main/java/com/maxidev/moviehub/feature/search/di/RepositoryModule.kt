package com.maxidev.moviehub.feature.search.di

import com.maxidev.moviehub.feature.search.data.repository.SearchRepositoryImpl
import com.maxidev.moviehub.feature.search.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository
}