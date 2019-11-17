package com.bettilina.cinemanteca.presentation.view.movie.movie

import android.util.Log
import androidx.lifecycle.ViewModel
import com.bettilina.cinemanteca.data.repository.movies.DatabaseMovieDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class MovieViewModel(private val dbDataStore: DatabaseMovieDataStore): ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

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

    fun isFavoriteMovie(movieID: Int): Int{
        var isFavorite = 0
        launch(Dispatchers.IO){
            try {
                isFavorite = dbDataStore.isFavoriteMovie(movieID)
            } catch (error: Exception){
                Log.d("REMOVE_FAV_EXC", "Exception when removing movie from favorites: " + error)
            }
        }
        return isFavorite
    }
}