package com.bettilina.cinemanteca.presentation.view.main.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bettilina.cinemanteca.data.model.Movie
import com.bettilina.cinemanteca.data.repository.MovieSourceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FavoritesViewModel(private val repository: MovieSourceRepository): ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    //Declarate LiveData attributes
    val movies: LiveData<List<Movie>>
        get() = localMovies

    val isLoading: LiveData<Boolean>
        get() = localIsLoading

    //Create MutableLiveData attributes
    private val localMovies = MutableLiveData<List<Movie>>()
    private val localIsLoading = MutableLiveData<Boolean>()

    fun loadFavMovies(){
        localIsLoading.postValue(true)
        launch(Dispatchers.IO){
            try {
                val movies = repository.getMovies()
                localMovies.postValue(movies)
            } catch (exception: Exception){

            } finally {
                localIsLoading.postValue(false)
            }
        }
    }
}