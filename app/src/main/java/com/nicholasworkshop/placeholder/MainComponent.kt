package com.nicholasworkshop.placeholder

import com.nicholasworkshop.placeholder.api.ApiModule
import com.nicholasworkshop.placeholder.fragment.UserFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [
    MainModule::class,
    ApiModule::class])
interface MainComponent {

    fun inject(userFragment: UserFragment)
}