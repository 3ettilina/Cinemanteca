package com.bettilina.cinemanteca.presentation.view.movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bettilina.cinemanteca.App
import com.bettilina.cinemanteca.R
import com.bettilina.cinemanteca.utils.Constants

class MovieActivity: AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.containerMovie, MovieFragment(), MovieFragmentTag)
            .commit()
    }

    companion object{
        private const val MovieFragmentTag = "MovieFragmentTag"
    }
}