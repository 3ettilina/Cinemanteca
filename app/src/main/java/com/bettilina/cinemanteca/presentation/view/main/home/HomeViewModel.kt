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
}