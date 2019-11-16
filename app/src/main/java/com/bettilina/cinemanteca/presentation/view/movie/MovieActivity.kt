package com.bettilina.cinemanteca.presentation.view.movie

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bettilina.cinemanteca.R
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
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)

    }
    companion object{
        private const val MovieFragmentTag = "MovieFragmentTag"
        private const val MovieReviewsTag = "ReviewsFragment"

    }


}