package com.nicholasworkshop.placeholder.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.nicholasworkshop.placeholder.MainApplication
import com.nicholasworkshop.placeholder.R
import com.nicholasworkshop.placeholder.model.MainDatabase
import com.nicholasworkshop.placeholder.model.UserDao
import kotlinx.android.synthetic.main.fragment_hometab.*
import javax.inject.Inject


class HomeTabFragment : Fragment() {

    companion object {

        private const val ARG_ID = "ARG_ID"

        fun newInstance(userId: Long): HomeTabFragment {
            val bundle = Bundle()
            bundle.putLong(ARG_ID, userId)
            val fragment = HomeTabFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject lateinit var mainDatabase: MainDatabase

    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity!!.application as MainApplication).component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_hometab, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val userId = arguments!!.getLong(ARG_ID)
        bottomNavigationView.setOnNavigationItemSelectedListener(NavigationItemSelectedListener())
        viewModel = ViewModelProviders
                .of(this, UserViewModel.Factory(mainDatabase.userDao()))
                .get(UserViewModel::class.java)
        viewModel.userDao.findById(userId).observe(this, Observer {
            (activity as AppCompatActivity).supportActionBar!!.title = it?.name
        })
        childFragmentManager.beginTransaction()
                .replace(R.id.contentView, UserDetailFragment.newInstance(userId))
                .commit()
    }

    inner class NavigationItemSelectedListener : BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            val userId = arguments!!.getLong(ARG_ID)
            when (item.itemId) {
                R.id.navigation_home -> {
                    childFragmentManager.beginTransaction()
                            .replace(R.id.contentView, UserDetailFragment.newInstance(userId))
                            .commit()
                    return true
                }
                R.id.navigation_albums -> {
                    childFragmentManager.beginTransaction()
                            .replace(R.id.contentView, AlbumFragment.newInstance(userId))
                            .commit()
                    return true
                }
                R.id.navigation_posts -> {
                    childFragmentManager.beginTransaction()
                            .replace(R.id.contentView, PostFragment.newInstance(userId))
                            .commit()
                    return true
                }
                R.id.navigation_todos -> {
                    childFragmentManager.beginTransaction()
                            .replace(R.id.contentView, ToDoFragment.newInstance(userId))
                            .commit()
                    return true
                }
            }
            return false
        }
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
}
