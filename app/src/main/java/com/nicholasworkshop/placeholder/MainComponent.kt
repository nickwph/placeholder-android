package com.nicholasworkshop.placeholder

import com.nicholasworkshop.placeholder.api.ApiModule
import com.nicholasworkshop.placeholder.fragment.*
import com.nicholasworkshop.placeholder.model.DatabaseModule
import com.nicholasworkshop.placeholder.model.MainDatabase
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [
    MainModule::class,
    DatabaseModule::class,
    ApiModule::class])
interface MainComponent {

    fun inject(userFragment: UserFragment)

    fun inject(userFragment: AlbumFragment)

    fun inject(photoFragment: PhotoFragment)

    fun inject(homeTabFragment: HomeTabFragment)

    fun inject(postFragment: PostFragment)

    fun inject(toDoFragment: ToDoFragment)
}