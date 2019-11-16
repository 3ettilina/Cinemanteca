package com.bettilina.cinemanteca.presentation.view.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bettilina.cinemanteca.data.model.Genre
import com.bettilina.cinemanteca.data.repository.MovieSourceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class MovieGenresViewModel(private val repository: MovieSourceRepository): ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    val genres: LiveData<List<Genre>>
        get() = localGenres

    val isLoading: LiveData<Boolean>
        get() = localIsLoading

    private val localGenres = MutableLiveData<List<Genre>>()
    private val localIsLoading = MutableLiveData<Boolean>()

    fun loadGenres(){
        localIsLoading.postValue(true)
        launch(Dispatchers.IO){
            try {

                val moviesGenreList = repository.getMoviesGenres()
                localGenres.postValue(moviesGenreList)
            } catch (error: Exception){
                Log.d("LOAD_MOVIES_EXCEPTION", "Exception when loading movies: " + error)
            } finally {
                localIsLoading.postValue(false)
            }
        }
    }
}