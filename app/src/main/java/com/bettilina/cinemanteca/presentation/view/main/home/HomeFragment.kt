package com.bettilina.cinemanteca.presentation.view.main.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RatingBar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bettilina.cinemanteca.R
import com.bettilina.cinemanteca.data.model.Genre
import com.bettilina.cinemanteca.data.model.Movie
import com.bettilina.cinemanteca.presentation.helper.visibleIf
import com.bettilina.cinemanteca.presentation.view.main.adapter.MovieAdapter
import com.bettilina.cinemanteca.presentation.view.movie.MovieGenresViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.internal.wait
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
        viewModel.search.observe(viewLifecycleOwner, Observer(this::searchLoading))
        viewModel.filter.observe(viewLifecycleOwner, Observer(this::setFilter))

        genreViewModel.genres.observe(viewLifecycleOwner, Observer(this::genresLoaded))

        //Call viewModel methods
        viewModel.loadMovies()
        genreViewModel.loadGenres()

        adapter.context = context

        txt_MovieSearch.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,before: Int, count: Int) {
                if (s.isNotEmpty()) viewModel.searchMovie(s.toString()) else viewModel.loadMovies()
            }
        })

        txt_MovieSearch.onRightDrawableClicked {
            it.text.clear()
        }

        ratingBar_Search.setOnRatingBarChangeListener{_,rating,_ ->

            if(rating.toInt()>0){
                val init:Int = (rating.toInt() * 2) - 2
                val end:Int = (rating.toInt() * 2)
                viewModel.setFilter(init,end,txt_MovieSearch.text.isNotEmpty())
            }else{
                if (txt_MovieSearch.text.isNotEmpty()) viewModel.searchMovie(txt_MovieSearch.text.toString()) else viewModel.loadMovies()
            }
        }
    }

    private fun moviesLoaded(movies: List<Movie>){
        adapter.movies = movies
    }

    private fun searchLoading(movies: List<Movie>){
        adapter.movies = movies
    }

    private fun setFilter(movies: List<Movie>){
        adapter.movies = movies
    }

    private fun genresLoaded(genres: List<Genre>){
        adapter.genres = genres
    }

    private fun loadingStateChange(isLoading: Boolean){
        loading_Movies.visibleIf(isLoading)
        recyclerView_Movies.visibleIf(!isLoading)
    }

    private fun EditText.onRightDrawableClicked(onClicked: (view: EditText) -> Unit) {
        this.setOnTouchListener { v, event ->
            var hasConsumed = false
            if (v is EditText) {
                if (event.x >= v.width - v.totalPaddingRight) {
                    if (event.action == MotionEvent.ACTION_UP) {
                        onClicked(this)
                    }
                    hasConsumed = true
                }
            }
            hasConsumed
        }
    }

}
