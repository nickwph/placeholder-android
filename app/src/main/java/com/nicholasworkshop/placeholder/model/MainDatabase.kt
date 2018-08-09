package com.nicholasworkshop.placeholder.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(
        version = 1,
        entities = [
            User::class,
            Album::class])
abstract class MainDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun albumDao(): AlbumDao
}