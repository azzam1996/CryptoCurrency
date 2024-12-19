package com.azzam.cryptocurrency.currencies.di

import com.azzam.cryptocurrency.currencies.data.api.Api
import com.azzam.cryptocurrency.currencies.data.repository.RepositoryImpl
import com.azzam.cryptocurrency.currencies.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {
    @Provides
    @Singleton
    fun provideRepository(api: Api): Repository {
        return RepositoryImpl(api)
    }
}