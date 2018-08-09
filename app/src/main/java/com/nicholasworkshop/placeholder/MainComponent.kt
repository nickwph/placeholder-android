package com.nicholasworkshop.placeholder

import com.nicholasworkshop.placeholder.api.ApiModule
import com.nicholasworkshop.placeholder.fragment.AlbumFragment
import com.nicholasworkshop.placeholder.fragment.HomeTabFragment
import com.nicholasworkshop.placeholder.fragment.PhotoFragment
import com.nicholasworkshop.placeholder.fragment.UserFragment
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
}