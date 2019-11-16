package com.bettilina.cinemanteca.presentation.view.main.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bettilina.cinemanteca.R
import com.bettilina.cinemanteca.data.model.Genre
import com.bettilina.cinemanteca.data.model.Movie
import com.bettilina.cinemanteca.presentation.helper.visibleIf
import com.bettilina.cinemanteca.presentation.view.main.adapter.MovieAdapter
import com.bettilina.cinemanteca.presentation.view.movie.MovieGenresViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val adapter = MovieAdapter()
    private val viewModel: HomeViewModel by viewModel()
    private val genreViewModel:MovieGenresViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Associate the layout manager and adapter to the RecyclerView
        recyclerView_Movies.apply{
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = this@HomeFragment.adapter
        }

        //Observe changes of data/flags on the viewModel
        viewModel.movies.observe(viewLifecycleOwner, Observer(this::moviesLoaded))
        viewModel.isLoading.observe(viewLifecycleOwner, Observer(this::loadingStateChange))

        genreViewModel.genres.observe(viewLifecycleOwner, Observer(this::genresLoaded))

        //Call viewModel methods
        viewModel.loadMovies()
        genreViewModel.loadGenres()

        adapter.context = context
    }

    private fun moviesLoaded(movies: List<Movie>){
        adapter.movies = movies
    }

    private fun genresLoaded(genres: List<Genre>){
        adapter.genres = genres
    }

    private fun loadingStateChange(isLoading: Boolean){
        loading_Movies.visibleIf(isLoading)
        recyclerView_Movies.visibleIf(!isLoading)
    }

}
