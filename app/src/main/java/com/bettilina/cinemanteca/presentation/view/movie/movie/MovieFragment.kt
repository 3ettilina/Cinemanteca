package com.bettilina.cinemanteca.presentation.view.movie.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bettilina.cinemanteca.R
import com.bettilina.cinemanteca.presentation.view.movie.reviews.ReviewsFragment
import com.bettilina.cinemanteca.utils.Constants
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : Fragment(){

    private var title: String? = ""
    private var average: String? = ""
    private var genre: String? = ""
    private var imageDir: String? = ""
    private var year: String? = ""
    private var description: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie, container, false)

            /*Obteniendo valores de la película a través del intent*/
            activity?.let{
                val bundle:Bundle? = it.intent.extras
                bundle?.let{bundle->
                    title = bundle.getString(Constants.MOVIE_TITLE,"")
                    average = bundle.getString(Constants.MOVIE_AVERAGE,"")
                    genre = bundle.getString(Constants.MOVIE_GENRE,"")
                    imageDir = bundle.getString(Constants.MOVIE_IMAGE_DIR,"")
                    year = bundle.getString(Constants.MOVIE_YEAR,"")
                    description = bundle.getString(Constants.MOVIE_DESCRIPTION,"")
                }
            }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txt_TitleMovie.text = title
        txt_GenreMovie.text = genre
        txt_RankMovie.text = average
        txt_YearMovie.text = year
        txt_DescriptionMovie.text = description

        Glide
            .with(view)
            .load(imageDir)
            .centerCrop()
            .into(view.findViewById(R.id.img_PosterMovie))

        btn_Reviews.setOnClickListener{
            fragmentManager!!
                .beginTransaction()
                .replace(R.id.containerMovie, ReviewsFragment.newInstance())
                .commit()
        }
    }
}

