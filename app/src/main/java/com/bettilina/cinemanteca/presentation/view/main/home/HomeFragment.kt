package com.bettilina.cinemanteca.presentation.view.main.home

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bettilina.cinemanteca.App
import com.bettilina.cinemanteca.R
import com.bettilina.cinemanteca.data.model.Genre
import com.bettilina.cinemanteca.data.model.Movie
import com.bettilina.cinemanteca.presentation.helper.visibleIf
import com.bettilina.cinemanteca.presentation.view.main.adapter.MovieAdapter
import com.bettilina.cinemanteca.presentation.view.main.helper.CustomRecyclerViewItemTouchListener
import com.bettilina.cinemanteca.presentation.view.movie.MovieActivity
import com.bettilina.cinemanteca.presentation.view.movie.MovieGenresViewModel
import com.bettilina.cinemanteca.utils.Constants
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.view_movie.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private val adapter = MovieAdapter()
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
        genreViewModel.genres.observe(viewLifecycleOwner, Observer(this::genresLoaded))
        viewModel.isLoading.observe(viewLifecycleOwner, Observer(this::loadingStateChange))

        //Call viewModel methods
        viewModel.loadMovies(ratingBar_Search.rating, txt_MovieSearch.text.toString())
        genreViewModel.loadGenres()

        adapter.context = context

        //Set listeners
        setSearchTextChangeListener()
        setRatingBarChangeListener()
        setClearSearchFieldClick()
        setMovieItemListeners(recyclerView_Movies)
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

    private fun setSearchTextChangeListener(){
        txt_MovieSearch.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence, start: Int,before: Int, count: Int) {
                viewModel.loadMovies(ratingBar_Search.rating, s.toString())
            }
        })
    }

    private fun setRatingBarChangeListener(){
        ratingBar_Search.setOnRatingBarChangeListener{_,rating,_ ->
            viewModel.loadMovies(rating,txt_MovieSearch.text.toString())
        }
    }

    private fun setClearSearchFieldClick(){
        txt_MovieSearch.onRightDrawableClicked {
            it.text.clear()
        }
    }

    private fun setMovieItemListeners(recyclerView: RecyclerView) {
        recyclerView_Movies.addOnItemTouchListener(CustomRecyclerViewItemTouchListener(recyclerView,
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
        itemView.chk_Fav.isChecked = !itemView.chk_Fav.isChecked

        if(itemView.chk_Fav.isChecked){
            //Change button background color
            itemView.btn_Favorites.
                setBackgroundColor(Color.parseColor(context!!.getString(R.string.FavColor)))
            //Update movie as favorite one on database
            val movieId = adapter.movies[position].id
            viewModel.addFavoriteMovie(movieId)

            //Show message to user
            Toast.makeText(view?.context,
                "Movie added to favorites!",
                Toast.LENGTH_LONG).show()

            //Change the button text
            itemView.txt_Favorites.setText(R.string.remove_favs_button)

        }else{
            //Change button background color
            itemView.btn_Favorites.
                setBackgroundColor(Color.parseColor(context!!.getString(R.string.NoFavColor)))

            //Update movie as favorite one on database
            val movieId = adapter.movies[position].id
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
            putExtra(Constants.MOVIE_GENRE, "Comedy")
            putExtra(
                Constants.MOVIE_IMAGE_DIR,
                App.imgSrcBasePath500 + currentMovie.posterPath
            )
            putExtra(Constants.MOVIE_YEAR, currentMovie.releaseDate)
            putExtra(Constants.MOVIE_DESCRIPTION, currentMovie.description)
        }
        ContextCompat.startActivity(context!!, intent, null)
    }

    @SuppressLint("ClickableViewAccessibility")
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
