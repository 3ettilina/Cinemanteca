package com.bettilina.cinemanteca.presentation.view.main.favorites

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bettilina.cinemanteca.App
import com.bettilina.cinemanteca.R
import com.bettilina.cinemanteca.data.model.Movie
import com.bettilina.cinemanteca.presentation.helper.gone
import com.bettilina.cinemanteca.presentation.helper.visible
import com.bettilina.cinemanteca.presentation.helper.visibleIf
import com.bettilina.cinemanteca.presentation.view.main.adapter.MovieAdapter
import com.bettilina.cinemanteca.presentation.view.main.helper.CustomRecyclerViewItemTouchListener
import com.bettilina.cinemanteca.presentation.view.movie.MovieActivity
import com.bettilina.cinemanteca.utils.Constants
import com.bettilina.cinemanteca.utils.OrderCriterial
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.view_movie.view.*
import org.koin.android.ext.android.inject

class FavoritesFragment: Fragment() {

    private val viewModel: FavoritesViewModel by inject()
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_favorites, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MovieAdapter(Glide.with(this))

        //Associate the layout manager and adapter to the RecyclerView
        recyclerView_FavMovies.apply {
            layoutManager = GridLayoutManager(context,
                2,
                GridLayoutManager.VERTICAL,
                false)
            adapter = this@FavoritesFragment.adapter
        }

        //Call viewModel methods

        viewModel.loadFavMovies()

        //Attach observers to notify local methods
        viewModel.movies.observe(viewLifecycleOwner, Observer(this::favMoviesLoaded))
        viewModel.isLoading.observe(viewLifecycleOwner, Observer(this::loadingStateChanged))

        //Set listeners
        setMovieItemListeners(recyclerView_FavMovies)
    }

    private fun favMoviesLoaded(movies: List<Movie>){
        if(movies.size>0){
            text_nonFavs.gone()
            relativeL_Favs.visible()
            adapter.movies = movies
        }else{
            text_nonFavs.visible()
            relativeL_Favs.gone()
        }

    }

    private fun loadingStateChanged(isLoading : Boolean){
        loading_Favs.visibleIf(isLoading)
        recyclerView_FavMovies.visibleIf(!isLoading)
    }

    private fun setMovieItemListeners(recyclerView: RecyclerView) {
        recyclerView_FavMovies.addOnItemTouchListener(
            CustomRecyclerViewItemTouchListener(recyclerView,
                intArrayOf(R.id.btn_Favorites, R.id.containerMovie),
                object : CustomRecyclerViewItemTouchListener.MyCustomClickListener {
                    override fun onFavoriteClick(itemView: View, position: Int) {
                        handleFavoriteClick(itemView, position)
                    }

                    override fun onMovieClick(view: View, position: Int) {
                        handleMovieClick(position)
                    }

                    override fun onClick(view: View, position: Int) {}

                    override fun onLongClick(view: View, position: Int) {}
                })
        )
    }

    private fun handleFavoriteClick(itemView: View, position: Int){
        //Check to handle button state
        //itemView.chk_Fav.isChecked = !itemView.chk_Fav.isChecked

        val currentMovie = adapter.movies[position]

        if(currentMovie.isFavorite == 0){
            //Change button background color
            itemView.btn_Favorites.background =
                resources.getDrawable(R.drawable.movie_button_border_remove_fav)
            //Update movie as favorite one on database
            val movieId = currentMovie.id
            viewModel.addFavoriteMovie(movieId)

            //Show message to user
            Toast.makeText(view?.context,
                "Movie added to favorites!",
                Toast.LENGTH_LONG).show()

            //Change the button text
            itemView.txt_Favorites.setText(R.string.remove_favs_button)

        } else {
            //Change button background color
            itemView.btn_Favorites.background =
                resources.getDrawable(R.drawable.movie_button_border_add_fav)

            //Update movie as favorite one on database
            val movieId = currentMovie.id
            viewModel.removeFavoriteMovie(movieId)

            //Show message to user
            Toast.makeText(view?.context,
                "Movie removed from favorites!",
                Toast.LENGTH_LONG).show()

            //Change the button text
            itemView.txt_Favorites.setText(R.string.add_favs_button)
        }
    }

    private fun handleMovieClick(position: Int){
        val currentMovie = adapter.movies[position]

        val intent = Intent(context, MovieActivity::class.java).apply {
            putExtra(Constants.MOVIE_TITLE, currentMovie.title)
            putExtra(Constants.MOVIE_AVERAGE, currentMovie.voteAverage.toString())
            putExtra(
                Constants.MOVIE_IMAGE_DIR,
                App.imgSrcBasePath500 + currentMovie.posterPath
            )
            putExtra(Constants.MOVIE_YEAR, currentMovie.releaseDate)
            putExtra(Constants.MOVIE_DESCRIPTION, currentMovie.description)
            putExtra(Constants.MOVIE_ID,currentMovie.id)
            this.putExtra(Constants.MOVIE_IS_FAVORITE, currentMovie.isFavorite)
        }

        ContextCompat.startActivity(context!!, intent, null)
    }

    fun orderView(criterial : OrderCriterial){
        viewModel.orderView(criterial)
    }
}