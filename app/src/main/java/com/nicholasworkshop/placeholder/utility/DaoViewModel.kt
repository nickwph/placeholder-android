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