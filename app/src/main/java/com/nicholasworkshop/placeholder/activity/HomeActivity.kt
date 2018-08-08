package com.nicholasworkshop.placeholder.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nicholasworkshop.placeholder.R
import com.nicholasworkshop.placeholder.fragment.TabFragment

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportFragmentManager.beginTransaction()
                .replace(R.id.containerView, TabFragment())
                .commit()
    }
}
