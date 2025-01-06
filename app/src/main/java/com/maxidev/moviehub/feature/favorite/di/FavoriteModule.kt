package com.maxidev.moviehub.feature.favorite.di

import com.maxidev.moviehub.feature.favorite.data.repository.FavoriteRepositoryImpl
import com.maxidev.moviehub.feature.favorite.domain.repository.FavoriteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoriteModule {

    @Binds
    abstract fun bindsFavoriteRepository(impl: FavoriteRepositoryImpl): FavoriteRepository
}