package com.bettilina.cinemanteca.presentation.view.main.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bettilina.cinemanteca.App
import com.bettilina.cinemanteca.R
import com.bettilina.cinemanteca.data.model.Genre
import com.bettilina.cinemanteca.data.model.Movie
import com.bettilina.cinemanteca.presentation.view.movie.MovieActivity
import com.bettilina.cinemanteca.utils.Constants
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_movie.view.*

class MovieAdapter(): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var movies = listOf<Movie>()
        set(value) {
            field = value.sortedByDescending { it.popularity }
            notifyDataSetChanged()
        }

    var genres = listOf<Genre>()
        set(value) {
            field = value.sortedByDescending { it.title }
            notifyDataSetChanged()
        }

    var context: Context? = null

    //Associate the view with the viewHolder to modify its content
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.view_movie, parent, false)
            .let{view -> MovieViewHolder(view)}

    override fun getItemCount() = movies.size

    //Calls the bind function inside the viewHolder for each item on the list
    //so the viewHolder assigns each attribute of the item, to the corresponding fields on the view
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(movies[position])

    inner class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        //Assigns attributes of the movie to the itemView, to update the view contents.
        fun bind(movie: Movie){
            itemView.txt_Title.text = movie.title
            itemView.txt_Year.text = movie.releaseDate
            itemView.txt_Rank.text = movie.voteAverage.toString()
            Glide
                .with(itemView)
                .load(App.imgSrcBasePath + movie.posterPath)
                .centerCrop()
                .into(itemView.findViewById(R.id.img_Movie))


            itemView.setOnClickListener {vw->
                context?.let {
                    val intent = Intent(context, MovieActivity::class.java).apply {
                        putExtra(Constants.MOVIE_TITLE, movie.title)
                        putExtra(Constants.MOVIE_AVERAGE, movie.voteAverage.toString())
                        putExtra(Constants.MOVIE_GENRE, "Comedy")
                        putExtra(
                            Constants.MOVIE_IMAGE_DIR,
                            App.imgSrcBasePath500 + movie.posterPath
                        )
                        putExtra(Constants.MOVIE_YEAR, movie.releaseDate)
                        putExtra(Constants.MOVIE_DESCRIPTION, movie.description)
                    }
                    ContextCompat.startActivity(it, intent, null)
                }
            }

        }
    }
}