package com.maxidev.moviehub.feature.collection.di

import com.maxidev.moviehub.feature.collection.data.repository.CollectionRepositoryImpl
import com.maxidev.moviehub.feature.collection.domain.repository.CollectionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCollectionRepository(impl: CollectionRepositoryImpl): CollectionRepository
}