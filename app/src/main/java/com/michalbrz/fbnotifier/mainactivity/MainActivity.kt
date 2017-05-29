package com.michalbrz.fbnotifier.mainactivity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.michalbrz.fbnotifier.R
import com.michalbrz.fbnotifier.postslist.PostsFragment

class MainActivity : AppCompatActivity() {

    val TAG: String = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigation = findViewById(R.id.navigation) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        switchToFragment(MainFragment())
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> switchToFragment(MainFragment())
            R.id.navigation_all_posts -> switchToFragment(PostsFragment())
            R.id.navigation_notifications -> { }
        }
        true
    }

    private fun switchToFragment(fragment: Fragment) {
        Log.i(TAG, "Moving to ${fragment.javaClass.simpleName}")
        supportFragmentManager.beginTransaction().replace(R.id.fragment_holder, fragment).commit()
    }
}

