package com.bettilina.cinemanteca.presentation.view.movie

import android.os.Bundle
import android.provider.SyncStateContract
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bettilina.cinemanteca.R
import com.bettilina.cinemanteca.utils.Constants
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_movie.*
import org.threeten.bp.ZonedDateTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class MovieFragment : Fragment(){

    private var title: String? = ""
    private var average: String? = ""
    private var genre: String? = ""
    private var imageDir: String? = ""
    private var year: String? = ""
    private var descriprion: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vw:View =inflater.inflate(R.layout.fragment_movie, container, false)

            /*Obteniendo valores de la película a través del intent*/
            activity?.let{
                val bundle:Bundle? = it.intent.extras
                bundle?.let{bundle->
                    title = bundle.getString(Constants.MOVIE_TITLE,"")
                    average = bundle.getString(Constants.MOVIE_AVERAGE,"")
                    genre = bundle.getString(Constants.MOVIE_GENRE,"")
                    imageDir = bundle.getString(Constants.MOVIE_IMAGE_DIR,"")
                    year = bundle.getString(Constants.MOVIE_YEAR,"")
                    descriprion = bundle.getString(Constants.MOVIE_DESCRIPTION,"")
                }
            }

        val btn:Button = vw.findViewById(R.id.btn_Reviews)
        btn.setOnClickListener{
            Log.d("asdasd","asdasd")
        }

        return vw
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.txt_TitleMovie).text = title
        view.findViewById<TextView>(R.id.txt_GenreMovie).text = genre
        view.findViewById<TextView>(R.id.txt_RankMovie).text = average
        view.findViewById<TextView>(R.id.txt_YearMovie).text = year
        view.findViewById<TextView>(R.id.txt_DescriptionMovie).text = descriprion

        Glide
            .with(view)
            .load(imageDir)
            .centerCrop()
            .into(view.findViewById<ImageView>(R.id.img_PosterMovie))

    }


}

