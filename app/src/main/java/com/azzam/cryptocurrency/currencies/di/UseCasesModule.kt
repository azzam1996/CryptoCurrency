package com.azzam.cryptocurrency.currencies.di

import com.azzam.cryptocurrency.currencies.data.repository.RepositoryImpl
import com.azzam.cryptocurrency.currencies.domain.repository.Repository
import com.azzam.cryptocurrency.currencies.domain.usecase.GetTopTenCryptoCurrencies
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {
    @Provides
    @Singleton
    fun provideGetTopTenCryptoCurrencies(repository: Repository): GetTopTenCryptoCurrencies {
        return GetTopTenCryptoCurrencies(repository)
    }
}