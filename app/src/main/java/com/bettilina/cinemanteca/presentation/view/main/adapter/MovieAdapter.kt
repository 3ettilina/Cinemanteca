package com.bettilina.cinemanteca.presentation.view.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.bettilina.cinemanteca.App
import com.bettilina.cinemanteca.R
import com.bettilina.cinemanteca.data.model.Movie
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.view_movie.view.*

class MovieAdapter(private val glide: RequestManager): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var movies = listOf<Movie>()
        set(value) {
            field = value.sortedByDescending { it.popularity }
            notifyDataSetChanged()
        }

    var context: Context? = null

    //Associate the view with the viewHolder to modify its content
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.view_movie, parent, false)
            .let { view -> MovieViewHolder(view) }

    override fun getItemCount() = movies.size

    //Calls the bind function inside the viewHolder for each item on the list
    //so the viewHolder assigns each attribute of the item, to the corresponding fields on the view
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(movies[position])

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Assigns attributes of the movie to the itemView, to update the view contents.
        fun bind(movie: Movie) {
            itemView.txt_Title.text = movie.title
            itemView.txt_Year.text = movie.releaseDate
            itemView.txt_Rank.text = movie.voteAverage.toString()

            glide
                .load(App.imgSrcBasePath + movie.posterPath)
                .override(200, 350)
                .fitCenter()
                .into(itemView.findViewById(R.id.img_Movie))

            itemView.txt_Favorites.setText(setFavButtonText(movie))
        }

        private fun setFavButtonText(movie: Movie) =
            if (movie.isFavorite == 1) R.string.remove_favs_button
            else R.string.add_favs_button
    }
}