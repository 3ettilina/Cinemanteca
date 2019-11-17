package com.bettilina.cinemanteca.presentation.view.movie.reviews

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bettilina.cinemanteca.App
import com.bettilina.cinemanteca.data.model.Movie
import com.bettilina.cinemanteca.data.model.Review
import com.bettilina.cinemanteca.data.repository.MovieSourceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class ReviewsViewModel(private val repository: MovieSourceRepository) : ViewModel(), CoroutineScope {
    //Current thread
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    val reviews: LiveData<List<Review>>
        get() = localReviews

    val isLoading: LiveData<Boolean>
        get() = localIsLoading

    private val localReviews = MutableLiveData<List<Review>>()
    private val localIsLoading = MutableLiveData<Boolean>()

    fun loadReviewsMovies(movieId: Int){
        localIsLoading.postValue(true)
        launch(Dispatchers.IO){
            try {
                val reviewList = repository.getMovieReviews(movieId)
                localReviews.postValue(reviewList)
            } catch (error: Exception){
                Log.d("LOAD_REVIEW_EXCEPTION",
                    "Exception when loading movie review: " + error)
            } finally {
                localIsLoading.postValue(false)
            }
        }
    }
}
