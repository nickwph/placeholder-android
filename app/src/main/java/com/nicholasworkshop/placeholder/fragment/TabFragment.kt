package com.nicholasworkshop.placeholder.fragment

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.nicholasworkshop.placeholder.R
import kotlinx.android.synthetic.main.fragment_tab.*


class TabFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bottomNavigationView.setOnNavigationItemSelectedListener(NavigationItemSelectedListener())
    }

    class NavigationItemSelectedListener : BottomNavigationView.OnNavigationItemSelectedListener {
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
