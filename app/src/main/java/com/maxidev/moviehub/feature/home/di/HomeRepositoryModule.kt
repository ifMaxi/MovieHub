package com.maxidev.moviehub.feature.home.di

import com.maxidev.moviehub.feature.home.data.repository.HomeRepositoryImpl
import com.maxidev.moviehub.feature.home.domain.repository.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeRepositoryModule {

    @Binds
    abstract fun bindHomeRepository(impl: HomeRepositoryImpl): HomeRepository
}