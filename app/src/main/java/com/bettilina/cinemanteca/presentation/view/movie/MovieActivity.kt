package com.bettilina.cinemanteca.presentation.view.movie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bettilina.cinemanteca.R
import com.bettilina.cinemanteca.presentation.view.main.MainActivity
import com.bettilina.cinemanteca.presentation.view.movie.movie.MovieFragment

class MovieActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_movie)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.containerMovie,
                MovieFragment(), MovieFragmentTag)
            .commit()

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if(supportFragmentManager.backStackEntryCount == 0){
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    supportFragmentManager.popBackStack()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object{
        private const val MovieFragmentTag = "MovieFragmentTag"
    }


}