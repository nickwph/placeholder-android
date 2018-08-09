package com.nicholasworkshop.placeholder.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.epoxy.Typed2EpoxyController
import com.nicholasworkshop.placeholder.MainApplication
import com.nicholasworkshop.placeholder.R
import com.nicholasworkshop.placeholder.api.User
import com.nicholasworkshop.placeholder.api.UserService
import com.nicholasworkshop.placeholder.viewUserItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_user.*
import timber.log.Timber
import javax.inject.Inject

class UserFragment : Fragment() {

    @Inject lateinit var userService: UserService

    val userController = UserController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity!!.application as MainApplication).component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        epoxyRecyclerView.setController(userController)
        userService.getUserList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    userController.setData(it, false)
                }
    }
}

class UserController : Typed2EpoxyController<List<User>, Boolean>() {

    override fun buildModels(users: List<User>, loadingMore: Boolean) {
        for (user in users) {
            Timber.e("data user $user")
            viewUserItem {
                id(user.id)
                user(user)
            }
        }
    }
}