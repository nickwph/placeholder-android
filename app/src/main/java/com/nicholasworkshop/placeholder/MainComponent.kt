package com.nicholasworkshop.placeholder

import com.nicholasworkshop.placeholder.api.ApiModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [
    MainModule::class,
    ApiModule::class])
interface MainComponent {

}