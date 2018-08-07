package com.nicholasworkshop.placeholder

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import timber.log.Timber
import javax.inject.Singleton

@Module
class MainModule(
        private val application: Application) {

    init {
        Timber.plant(Timber.DebugTree())
    }

    @Provides
    @Singleton
    fun context(): Context {
        return application
    }

    @Provides
    @Singleton
    fun application(): Application {
        return application
    }
}