package com.nicholasworkshop.placeholder.fragment

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
import io.reactivex.Observable.fromCallable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity!!.application as MainApplication).component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_hometab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val userId = arguments!!.getLong(ARG_ID)
        fromCallable { mainDatabase.userDao().findById(userId) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { (activity as AppCompatActivity).supportActionBar!!.title = it.name }
        bottomNavigationView.setOnNavigationItemSelectedListener(NavigationItemSelectedListener())
        childFragmentManager.beginTransaction()
                .replace(R.id.contentView, AlbumFragment.newInstance(userId))
                .commit()
    }

    inner class NavigationItemSelectedListener : BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.navigation_home -> {
                    return true
                }
                R.id.navigation_dashboard -> {
                    return true
                }
                R.id.navigation_notifications -> {
                    return true
                }
            }
            return false
        }
    }
}
