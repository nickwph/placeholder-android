package com.nicholasworkshop.placeholder.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nicholasworkshop.placeholder.MainApplication
import com.nicholasworkshop.placeholder.databinding.FragmentUserdetailBinding
import com.nicholasworkshop.placeholder.model.MainDatabase
import com.nicholasworkshop.placeholder.model.UserDao
import com.nicholasworkshop.placeholder.utility.DaoViewModel
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

    private lateinit var viewModel: DaoViewModel<UserDao>
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
        viewModel = DaoViewModel.newInstance(this, UserDao::class.java, mainDatabase.userDao())
        viewModel.dao.findById(userId).observe(this, Observer {
            binding.user = it
            binding.executePendingBindings()
        })
    }
}
