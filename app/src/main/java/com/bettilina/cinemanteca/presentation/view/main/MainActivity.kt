package com.bettilina.cinemanteca.presentation.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.bettilina.cinemanteca.BottomSheetFragment
import com.bettilina.cinemanteca.R
import com.bettilina.cinemanteca.presentation.view.main.favorites.FavoritesFragment
import com.bettilina.cinemanteca.presentation.view.main.home.HomeFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_bottom_sheet.*

class MainActivity : AppCompatActivity() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>

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
        setSupportActionBar(findViewById(R.id.my_toolbar))

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        //btn_slide.text = "Slide Up"
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {

                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        // btn_slide.text = "Slide Down"
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {

                    }
                    BottomSheetBehavior.STATE_SETTLING -> {

                    }
                }
            }
        }
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_top_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.item_order -> {
            val modalBottomSheet = BottomSheetFragment()
            modalBottomSheet.show(supportFragmentManager, BottomSheetFragment.TAG)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
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
