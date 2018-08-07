package com.nicholasworkshop.placeholder

import android.app.Application

class MainApplication : Application() {

    lateinit var component: MainComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerMainComponent.builder()
                .mainModule(MainModule(this))
                .build()
    }
}