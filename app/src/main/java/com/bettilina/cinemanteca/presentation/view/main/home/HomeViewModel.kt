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

    val search: LiveData<List<Movie>>
        get() = localSearch

    val filter: LiveData<List<Movie>>
        get() = localFilter

    private val localMovies = MutableLiveData<List<Movie>>()
    private val localIsLoading = MutableLiveData<Boolean>()
    private val localSearch = MutableLiveData<List<Movie>>()
    private val localFilter = MutableLiveData<List<Movie>>()

    fun loadMovies(){
        localIsLoading.postValue(true)
        launch(Dispatchers.IO){
            try {
                val moviesList = repository.getMovies()
                localMovies.postValue(moviesList)
            } catch (error: Exception){
                Log.d("LOAD_MOVIES_EXCEPTION", "Exception when loading movies: " + error)
            } finally {
                localIsLoading.postValue(false)
            }
        }
    }

    fun searchMovie(txtSearch:String){
        localIsLoading.postValue(true)
        launch(Dispatchers.IO){
            try {
                val moviesList = repository.getMoviesBySearch(App.apiKey,txtSearch)
                localSearch.postValue(moviesList)
            } catch (error: Exception){
                Log.d("LOAD_MOVIES_EXCEPTION", "Exception when searching movies: " + error)
            } finally {
                localIsLoading.postValue(false)
            }
        }
    }

    fun setFilter(init:Int,end:Int,searching:Boolean){
        localIsLoading.postValue(true)
        if (searching){
            movies.value?.let{
                val filterList = it.filter { movie -> movie.voteAverage.toInt() in init..end }
                localFilter.postValue(filterList)
            }
        }else{
            search.value?.let{
                val filterList = it.filter { movie -> movie.voteAverage.toInt() in init..end }
                localFilter.postValue(filterList)
            }
        }
        localIsLoading.postValue(false)

    }
}