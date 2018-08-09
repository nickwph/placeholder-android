package com.nicholasworkshop.placeholder.model

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun mainDatabase(context: Context): MainDatabase {
        val klass = MainDatabase::class.java
        return Room.databaseBuilder(context, klass, klass.simpleName).build()
    }
}