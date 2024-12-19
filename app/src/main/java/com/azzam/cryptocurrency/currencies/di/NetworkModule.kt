package com.azzam.cryptocurrency.currencies.di

import android.app.Application
import com.azzam.cryptocurrency.currencies.data.api.Api
import com.azzam.cryptocurrency.currencies.data.api.RetrofitFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApi(app: Application): Api {
        return RetrofitFactory.create(app)
    }
}