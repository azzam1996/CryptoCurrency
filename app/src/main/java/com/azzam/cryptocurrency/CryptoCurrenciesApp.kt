package com.azzam.cryptocurrency

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class CryptoCurrenciesApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        when (BuildConfig.DEBUG) {
            true -> Timber.plant(Timber.DebugTree())
            false -> {
                // TODO Implement Release Tree
            }
        }
    }
}