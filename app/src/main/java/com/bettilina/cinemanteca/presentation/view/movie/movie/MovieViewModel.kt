package com.bettilina.cinemanteca.presentation.view.movie.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bettilina.cinemanteca.data.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class MovieViewModel(): ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    val movie: LiveData<Movie>
        get() = localMovie

    val isLoading: LiveData<Boolean>
        get() = localIsLoading

    private val localMovie = MutableLiveData<Movie>()
    private val localIsLoading = MutableLiveData<Boolean>()

    fun loadMovie(){
        localIsLoading.postValue(true)
    }

    fun searchMovie(search:String){

    }

}