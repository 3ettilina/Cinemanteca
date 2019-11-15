package com.bettilina.cinemanteca.presentation.view.main

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bettilina.cinemanteca.R
import com.bettilina.cinemanteca.presentation.view.main.favorites.FavoritesFragment
import com.bettilina.cinemanteca.presentation.view.main.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showFragment(HomeFragment(), HomeFragmentTag)
        bottomNav.setOnNavigationItemSelectedListener { menuItem ->
            removeActiveFragment()

            when(menuItem.itemId){
                R.id.home -> showFragment(HomeFragment(), HomeFragmentTag)
                R.id.favorites -> showFragment(FavoritesFragment(), FavsFragmentTag)
            }

            true
        }
    }

    private fun showFragment(fragment: Fragment, tag: String){
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, fragment, tag)
            .commit()
    }

    private fun removeActiveFragment(){
        listOf(HomeFragmentTag, FavsFragmentTag).forEach { fragmentTag ->
            val currentFragment = supportFragmentManager.findFragmentByTag(fragmentTag)
            currentFragment?.let {
                supportFragmentManager
                    .beginTransaction()
                    .remove(it)
                    .commit()
            }
        }
    }

    companion object{
        private const val HomeFragmentTag = "HomeFragmentTag"
        private const val FavsFragmentTag = "FavsFragmentTag"
    }
}
