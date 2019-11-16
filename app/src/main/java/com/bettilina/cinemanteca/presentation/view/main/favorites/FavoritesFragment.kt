package com.bettilina.cinemanteca.presentation.view.main.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bettilina.cinemanteca.R
import com.bettilina.cinemanteca.data.model.Movie
import com.bettilina.cinemanteca.presentation.helper.visibleIf
import com.bettilina.cinemanteca.presentation.view.main.adapter.MovieAdapter
import kotlinx.android.synthetic.main.fragment_favorites.*
import org.koin.android.ext.android.inject

class FavoritesFragment: Fragment() {

    private val adapter = MovieAdapter()
    private val viewModel: FavoritesViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_favorites, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Associate the layout manager and adapter to the RecyclerView
        rv_FavMovies.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = this@FavoritesFragment.adapter
        }

        //Call viewModel methods
        viewModel.loadFavMovies()

        //Attach observers to notify local methods
        viewModel.movies.observe(viewLifecycleOwner, Observer(this::favMoviesLoaded))
        viewModel.isLoading.observe(viewLifecycleOwner, Observer(this::loadingStateChanged))
    }

    private fun favMoviesLoaded(movies: List<Movie>){
        adapter.movies = movies
    }

    private fun loadingStateChanged(isLoading : Boolean){
        loading_Favs.visibleIf(isLoading)
        rv_FavMovies.visibleIf(!isLoading)
    }

}