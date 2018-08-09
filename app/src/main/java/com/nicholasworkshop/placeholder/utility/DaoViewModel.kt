package com.nicholasworkshop.placeholder.utility

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment


@Suppress("UNCHECKED_CAST")
class DaoViewModel<Dao>(val dao: Dao) : ViewModel() {

    companion object {
        fun <D, VM : DaoViewModel<D>> newInstance(fragment: Fragment, daoClass: Class<D>, dao: D): VM {
            return ViewModelProviders
                    .of(fragment, DaoViewModel.Factory(dao))
                    .get(DaoViewModel::class.java) as VM
        }
    }

    class Factory<D>(private val dao: D) : ViewModelProvider.NewInstanceFactory() {
        override fun <VM : ViewModel?> create(modelClass: Class<VM>): VM {
            return DaoViewModel(dao) as VM
        }
    }
}


@Suppress("UNCHECKED_CAST")
class Dao2ViewModel<Dao1, Dao2>(val dao1: Dao1, val dao2: Dao2) : ViewModel() {

    companion object {
        fun <D1, D2, VM : Dao2ViewModel<D1, D2>> newInstance(fragment: Fragment, dao1Class: Class<D1>, dao1: D1, dao2Class: Class<D2>, dao2: D2): VM {
            return ViewModelProviders
                    .of(fragment, Dao2ViewModel.Factory(dao1, dao2))
                    .get(DaoViewModel::class.java) as VM
        }
    }

    class Factory<D1, D2>(private val dao1: D1, private val dao2: D2) : ViewModelProvider.NewInstanceFactory() {
        override fun <VM : ViewModel?> create(modelClass: Class<VM>): VM {
            return Dao2ViewModel(dao1, dao2) as VM
        }
    }
}