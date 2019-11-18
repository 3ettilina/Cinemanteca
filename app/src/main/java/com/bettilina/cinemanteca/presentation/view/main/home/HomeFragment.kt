package com.bettilina.cinemanteca.presentation.view.main.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bettilina.cinemanteca.App
import com.bettilina.cinemanteca.R
import com.bettilina.cinemanteca.data.model.Movie
import com.bettilina.cinemanteca.presentation.helper.visibleIf
import com.bettilina.cinemanteca.presentation.view.main.adapter.MovieAdapter
import com.bettilina.cinemanteca.presentation.view.main.helper.CustomRecyclerViewItemTouchListener
import com.bettilina.cinemanteca.presentation.view.movie.MovieActivity
import com.bettilina.cinemanteca.utils.Constants
import com.bettilina.cinemanteca.utils.OrderCriterial
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.view_movie.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MovieAdapter(Glide.with(this))

        //Associate the layout manager and adapter to the RecyclerView
        recyclerView_Movies.apply{
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = this@HomeFragment.adapter
        }

        //Observe changes of data/flags on the viewModel
        viewModel.movies.observe(viewLifecycleOwner, Observer(this::moviesLoaded))
        viewModel.isLoading.observe(viewLifecycleOwner, Observer(this::loadingStateChange))

        //Call viewModel methods
        viewModel.loadMovies(ratingBar_Search.rating, txt_MovieSearch.text.toString())

        adapter.context = context

        //Set listeners
        setSearchTextChangeListener()
        setRatingBarChangeListener()
        setClearSearchFieldClick()
        setMovieItemListeners(recyclerView_Movies)
    }

    private fun moviesLoaded(movies: List<Movie>){
        movies.forEach {
            val isFav = viewModel.isFavoriteMovie(it.id)
            if(isFav == 1){
                it.isFavorite = 1
            }
        }
        adapter.movies = movies
        viewModel.saveMoviesToDB(movies)
    }

    private fun loadingStateChange(isLoading: Boolean){
        loading_Movies.visibleIf(isLoading)
        recyclerView_Movies.visibleIf(!isLoading)
    }

    private fun setSearchTextChangeListener(){
        txt_MovieSearch.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.loadMovies(ratingBar_Search.rating, s.toString())
            }
        })
    }

    private fun setRatingBarChangeListener(){
        ratingBar_Search.setOnRatingBarChangeListener{_,rating,_ ->
            viewModel.loadMovies(rating, txt_MovieSearch.text.toString())
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
                override fun onFavoriteClick(view: View, position: Int) {
                    handleFavoriteClick(view, position)
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
            addToFavorites(itemView, position)

        }else{
            removeFromFavorites(itemView, position)
        }
    }

    private fun addToFavorites(itemView: View, position: Int){
        //Change button background color
        itemView.btn_Favorites.setBackgroundResource(R.drawable.movie_button_border_remove_fav)
        //Update movie as favorite one on database
        val movieId = adapter.movies[position].id
        viewModel.addFavoriteMovie(movieId)

        //Show message to user
        Toast.makeText(view?.context,
            "Movie added to favorites!",
            Toast.LENGTH_LONG).show()

        //Change the button text
        itemView.txt_Favorites.setText(R.string.remove_favs_button)
    }

    private fun removeFromFavorites(itemView: View, position: Int){
        //Change button background color
        itemView.btn_Favorites.setBackgroundResource(R.drawable.movie_button_border_add_fav)

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

    private fun handleMovieClick(position: Int){
        val currentMovie = adapter.movies[position]

        val intent = Intent(context, MovieActivity::class.java).apply {
            this.putExtra(Constants.MOVIE_ID, currentMovie.id)
            this.putExtra(Constants.MOVIE_TITLE, currentMovie.title)
            this.putExtra(
                Constants.MOVIE_IMAGE_DIR,
                App.imgSrcBasePath + currentMovie.posterPath
            )
            this.putExtra(Constants.MOVIE_YEAR, currentMovie.releaseDate)
            this.putExtra(Constants.MOVIE_DESCRIPTION, currentMovie.description)
            this.putExtra(Constants.MOVIE_AVERAGE, currentMovie.voteAverage.toString())
            this.putExtra(Constants.MOVIE_IS_FAVORITE, currentMovie.isFavorite)
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

    fun orderView(criterial : OrderCriterial){
        viewModel.orderView(criterial)
    }

}
