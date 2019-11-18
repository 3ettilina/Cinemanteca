package com.bettilina.cinemanteca.presentation.view.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bettilina.cinemanteca.data.model.Movie
import com.bettilina.cinemanteca.data.repository.MovieSourceRepository
import com.bettilina.cinemanteca.data.repository.movies.DatabaseMovieDataStore
import com.bettilina.cinemanteca.utils.OrderCriterial
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class HomeViewModel(private val repository: MovieSourceRepository,
                    private val dbDataStore: DatabaseMovieDataStore): ViewModel(), CoroutineScope {
    //Current thread
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    val movies: LiveData<List<Movie>>
        get() = localMovies

    val isLoading: LiveData<Boolean>
        get() = localIsLoading

    private val localMovies = MutableLiveData<List<Movie>>()
    private val localIsLoading = MutableLiveData<Boolean>()


    fun loadMovies(ratingFilter: Float, query: String){
        if(query.isEmpty()){
            if(ratingFilter.toInt() == 0){
                loadMoviesStandard()
            } else {
                loadMoviesWithFilter(ratingFilter)
            }
        } else {
            if(ratingFilter.toInt() == 0){
                searchMoviesStandard(query)
            } else {
                searchMovieWithFilter(ratingFilter, query)
            }
        }
    }

    fun saveMoviesToDB(movies: List<Movie>){
        launch(Dispatchers.IO){
            try {
                val favMoviesSaved = dbDataStore.getFavoriteMovies()
                if(favMoviesSaved.isNotEmpty()){
                    dbDataStore.saveMovies(movies)
                    dbDataStore.updateFavMovies(favMoviesSaved)
                } else{
                    dbDataStore.saveMovies(movies)
                }
            } catch (error: Exception){
                Log.d("ADD_MOVIE_EXC", "Exception when adding movies list: " + error)
            }
        }
    }

    fun addFavoriteMovie(movieId: Int){
        launch(Dispatchers.IO){
            try {
                dbDataStore.addFavorite(movieId)
                val isfav = isFavoriteMovie(movieId)
            } catch (error: Exception){
                Log.d("ADD_FAVS_EXC", "Exception when adding movie to favorites: " + error)
            }
        }
    }

    fun removeFavoriteMovie(movieID: Int){
        launch(Dispatchers.IO){
            try {
                dbDataStore.removeFavorite(movieID)
            } catch (error: Exception){
                Log.d("REMOVE_FAV_EXC", "Exception when removing movie from favorites: " + error)
            }
        }
    }

    fun isFavoriteMovie(movieID: Int): Int{
        var isFavorite = 0

        launch(Dispatchers.IO){
            try {
                isFavorite = dbDataStore.isFavoriteMovie(movieID)
            } catch (error: Exception){
                Log.d("REMOVE_FAV_EXC", "Exception when removing movie from favorites: " + error)
            }
        }
        /*Sin el sleep la operación retorna antes de que la corrutina finalice, por lo que siempre devuelve false*/
        Thread.sleep(100)
        return isFavorite
    }

    private fun loadMoviesStandard(){
        localIsLoading.postValue(true)
        launch(Dispatchers.IO){
            try {
                val movies = repository.getMovies()
                localMovies.postValue(movies)
            } catch (error: Exception){
                Log.d("LOAD_MOVIES_EXCEPTION",
                    "Exception when loading movies by standard method: " + error)
            } finally {
                localIsLoading.postValue(false)
            }
        }
    }

    private fun searchMoviesStandard(query: String){
        localIsLoading.postValue(true)
        launch(Dispatchers.IO){
            try {
                val movies = repository.getMoviesBySearch(query)
                localMovies.postValue(movies)
            } catch (error: Exception){
                Log.d("LOAD_MOVIES_EXCEPTION",
                    "Exception when loading movies by standard search: " + error)
            } finally {
                localIsLoading.postValue(false)
            }
        }
    }

    private fun loadMoviesWithFilter(ratingFilter: Float){
        localIsLoading.postValue(true)
        launch(Dispatchers.IO){
            try {
                val init:Int = (ratingFilter.toInt() *2 ) -2
                val end:Int = (ratingFilter.toInt() *2 )

                val moviesList = repository.getMoviesByVoteAvg(init, end)

                localMovies.postValue(moviesList)
            } catch (error: Exception){
                Log.d("LOAD_MOVIES_EXCEPTION",
                    "Exception when loading movies: " + error)
            } finally {
                localIsLoading.postValue(false)
            }
        }
    }

    private fun searchMovieWithFilter(ratingFilter: Float, txtSearch: String) {
        localIsLoading.postValue(true)
        launch(Dispatchers.IO) {
            try {
                val moviesList = repository.getMoviesBySearch(txtSearch)
                //TODO: Call endpoint instead of filtering the list
                val init: Int = (ratingFilter.toInt() * 2) - 2
                val end: Int = (ratingFilter.toInt() * 2)
                val filterList =
                    moviesList.filter { movie -> movie.voteAverage.toInt() in init..end }
                localMovies.postValue(filterList)
            } catch (error: Exception) {
                Log.d("LOAD_MOVIES_EXCEPTION",
                    "Exception when searching movies: " + error)
            } finally {
                localIsLoading.postValue(false)
            }
        }
    }

    fun orderView(criterial : OrderCriterial){
        val adapt = movies.value
        adapt?.let{
            var sortedList = when(criterial){
                OrderCriterial.AVERAGE -> adapt.sortedByDescending{ movie ->movie.voteAverage.toString() }
                OrderCriterial.TITLE -> adapt.sortedBy{ movie ->movie.title }
            }
            localMovies.postValue(sortedList)
        }
    }
}