package com.bettilina.cinemanteca.presentation.view.main.home

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bettilina.cinemanteca.App
import com.bettilina.cinemanteca.data.dao.MovieDao
import com.bettilina.cinemanteca.data.model.Movie
import com.bettilina.cinemanteca.data.repository.MovieSourceRepository
import com.bettilina.cinemanteca.data.repository.movies.DatabaseMovieDataStore
import com.bettilina.cinemanteca.data.service.MovieService
import com.bettilina.cinemanteca.utils.Constants
import com.bettilina.cinemanteca.utils.OrderCriterial
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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


    fun loadMovies(ratingFilter:Float,txtSearch:String){
        if(txtSearch.isEmpty()){
            loadRecomendedMovies(ratingFilter)
        }else {
            searchMovie(ratingFilter,txtSearch)
        }
    }

    fun addFavoriteMovie(movieId: Int){
        launch(Dispatchers.IO){
            try {
                dbDataStore.addFavorite(movieId)
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

    private fun loadRecomendedMovies(ratingFilter:Float){
        localIsLoading.postValue(true)
        launch(Dispatchers.IO){
            try {
                val moviesList = repository.getMovies()
                if(ratingFilter.toInt()==0) {
                    localMovies.postValue(moviesList)
                }else{
                    //TODO: Change filter for api usage
                    val init:Int = (ratingFilter.toInt()*2) -2
                    val end:Int = (ratingFilter.toInt()*2)
                    val filterList = moviesList.filter { movie -> movie.voteAverage.toInt() in init..end }
                    localMovies.postValue(filterList)
                }
            } catch (error: Exception){
                Log.d("LOAD_MOVIES_EXCEPTION", "Exception when loading movies: " + error)
            } finally {
                localIsLoading.postValue(false)
            }
        }
    }

    private fun searchMovie(ratingFilter: Float, txtSearch: String){
        localIsLoading.postValue(true)
        launch(Dispatchers.IO){
            try {
                val moviesList = repository.getMoviesBySearch(App.apiKey,txtSearch)
                if(ratingFilter.toInt() == 0) {
                    localMovies.postValue(moviesList)
                }else{
                    //TODO: Call endpoint instead of filtering the list
                    val init:Int = (ratingFilter.toInt() *2 ) -2
                    val end:Int = (ratingFilter.toInt() *2 )
                    val filterList = moviesList.filter { movie -> movie.voteAverage.toInt() in init..end }
                    localMovies.postValue(filterList)
                }
            } catch (error: Exception){
                Log.d("LOAD_MOVIES_EXCEPTION", "Exception when searching movies: " + error)
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