package com.bettilina.cinemanteca.presentation.view.main.home

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bettilina.cinemanteca.App
import com.bettilina.cinemanteca.data.model.Movie
import com.bettilina.cinemanteca.data.repository.MovieSourceRepository
import com.bettilina.cinemanteca.data.service.MovieService
import com.bettilina.cinemanteca.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class HomeViewModel(private val repository: MovieSourceRepository): ViewModel(), CoroutineScope {
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

    private fun loadRecomendedMovies(ratingFilter:Float){
        localIsLoading.postValue(true)
        launch(Dispatchers.IO){
            try {
                val moviesList = repository.getMovies()
                if(ratingFilter.toInt()==0) {
                    localMovies.postValue(moviesList)
                }else{
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

    private fun searchMovie(ratingFilter:Float,txtSearch:String){

        localIsLoading.postValue(true)
        launch(Dispatchers.IO){
            try {
                val moviesList = repository.getMoviesBySearch(App.apiKey,txtSearch)
                if(ratingFilter.toInt()==0) {
                    localMovies.postValue(moviesList)
                }else{
                    val init:Int = (ratingFilter.toInt()*2) -2
                    val end:Int = (ratingFilter.toInt()*2)
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


}