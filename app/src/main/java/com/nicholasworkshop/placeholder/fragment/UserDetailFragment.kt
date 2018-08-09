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
import com.nicholasworkshop.placeholder.MainApplication
import com.nicholasworkshop.placeholder.databinding.FragmentUserdetailBinding
import com.nicholasworkshop.placeholder.model.MainDatabase
import com.nicholasworkshop.placeholder.model.UserDao
import javax.inject.Inject


class UserDetailFragment : Fragment() {

    companion object {

        private const val ARG_ID = "ARG_ID"

        fun newInstance(userId: Long): UserDetailFragment {
            val bundle = Bundle()
            bundle.putLong(ARG_ID, userId)
            val fragment = UserDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject lateinit var mainDatabase: MainDatabase

    private lateinit var viewModel: UserDetailViewModel
    private lateinit var binding: FragmentUserdetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity!!.application as MainApplication).component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentUserdetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val userId = arguments?.getLong(ARG_ID)!!
        viewModel = ViewModelProviders
                .of(this, UserDetailViewModel.Factory(mainDatabase.userDao()))
                .get(UserDetailViewModel::class.java)
        viewModel.userDao.findById(userId).observe(this, Observer {
            binding.user = it
            binding.executePendingBindings()
        })
    }

    class UserDetailViewModel(
            val userDao: UserDao
    ) : ViewModel() {

        @Suppress("UNCHECKED_CAST")
        class Factory(
                private val userDao: UserDao
        ) : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return UserDetailViewModel(userDao) as T
            }
        }
    }
}
