package com.nicholasworkshop.placeholder.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.epoxy.TypedEpoxyController
import com.nicholasworkshop.placeholder.MainApplication
import com.nicholasworkshop.placeholder.R
import com.nicholasworkshop.placeholder.api.UserService
import com.nicholasworkshop.placeholder.model.MainDatabase
import com.nicholasworkshop.placeholder.model.User
import com.nicholasworkshop.placeholder.model.UserDao
import com.nicholasworkshop.placeholder.model.adapter.parse
import com.nicholasworkshop.placeholder.viewUserItem
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_user.*
import timber.log.Timber
import javax.inject.Inject

class UserFragment : Fragment() {

    @Inject lateinit var userService: UserService
    @Inject lateinit var mainDatabase: MainDatabase

    private lateinit var viewModel: UserViewModel
    private val userController = UserEpoxyController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity!!.application as MainApplication).component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        epoxyRecyclerView.setController(userController)
        viewModel = ViewModelProviders
                .of(this, UserViewModel.Factory(mainDatabase.userDao()))
                .get(UserViewModel::class.java)
        viewModel.userDao.all().observe(this, Observer {
            userController.setData(it)
        })
        userService.getUserList()
                .subscribeOn(Schedulers.io())
                .subscribe { viewModel.userDao.insertAll(parse(it)) }
    }


    class UserViewModel(
            val userDao: UserDao
    ) : ViewModel() {

        @Suppress("UNCHECKED_CAST")
        class Factory(
                private val userDao: UserDao
        ) : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return UserViewModel(userDao) as T
            }
        }
    }

    inner class UserEpoxyController : TypedEpoxyController<List<User>>() {

        override fun buildModels(users: List<User>) {
            for (user in users) {
                Timber.e("data user $user")
                viewUserItem {
                    id(user.id)
                    user(user)
                    clickListener { v ->
                        Timber.e("hi user $user")
                        fragmentManager!!.beginTransaction()
                                .addToBackStack(null)
                                .replace(R.id.containerView, HomeTabFragment.newInstance(user.id))
                                .commit()
                    }
                }
            }
        }
    }
}