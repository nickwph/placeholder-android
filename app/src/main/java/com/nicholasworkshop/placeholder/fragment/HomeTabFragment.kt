package com.nicholasworkshop.placeholder.fragment

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.nicholasworkshop.placeholder.R
import kotlinx.android.synthetic.main.fragment_hometab.*


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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_hometab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val userId = arguments!!.getLong(ARG_ID)
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
