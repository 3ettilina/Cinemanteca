package com.bettilina.cinemanteca.data.repository.movies

import android.util.Log
import com.bettilina.cinemanteca.data.dao.MovieDao
import com.bettilina.cinemanteca.data.model.Movie
import com.bettilina.cinemanteca.data.model.Review
import com.bettilina.cinemanteca.data.service.response.MovieResponse
import com.bettilina.cinemanteca.data.service.response.ReviewResponse

open class DatabaseMovieDataStore (private val movieDao: MovieDao): MovieDataStore{

    override suspend fun getMovies(): List<Movie> {
        var movies = listOf<Movie>()
        try {
            movies = movieDao.getMovies()
            Log.d("DB_MOVIES_GET", "Successful retrieval of movies list")
        } catch (error: Exception){
            Log.d("DB_MOVIES_GET_ERR", "Error while trying to retrieve list of movies: $error")
        }
        return movies
    }

    override suspend fun getMoviesByVoteAvg(
        minVote: Int,
        maxVote: Int
    ): List<Movie> {
        return movieDao.getMovies()
    }

    override suspend fun getMoviesBySearch(txtSearch: String): List<Movie> {
        return MovieResponse(listOf()).movieList
    }

    override suspend fun getMovieReviews(id: Int): List<Review> {
        return ReviewResponse(listOf()).reviewList
    }

    suspend fun saveMovies(movies: List<Movie>){
        try {
            movieDao.insertMovies(movies)
            Log.d("DB_MOVIES_ADD", "Successful addition of movies list")
        } catch (error: Exception){
            Log.d("DB_MOVIES_ADD_ERR", "Error while trying to add a list of movies: $error")
        }
    }

    override suspend fun existsMovie(id: Int): Boolean {
        var existsMovie = false
        try {
            existsMovie = movieDao.doesMovieExist(id)
            Log.d("DB_MOVIES_EXISTS", "Consulting if movie exists in database")
        } catch (error: Exception){
            Log.d("DB_MOVIES_EXISTS_ERR", "Error while Consulting if movie exists in database: $error")
        }
        return existsMovie
    }

    suspend fun updateFavMovies(movies: List<Movie>){
        try {
            movieDao.updateFavMovies(movies)
            Log.d("DB_MOVIES_UPDATE", "Successful update of movies")
        } catch (error: Exception){
            Log.d("DB_MOVIES_UPD_ERR", "Error while trying to update the movies: $error")
        }
    }

    suspend fun getFavoriteMovies(): List<Movie>{
        var movies: List<Movie> = listOf()
        try {
            movies = movieDao.getFavoriteMovies()
            Log.d("DB_GET_FAV", "Successful retrieval of favorite movie")
        } catch (error: Exception){
            Log.d("DB_GET_FAV_ERR", "Error while trying to retrieve a favorite movie: $error")
        }
        return movies
    }

    suspend fun addFavorite(movieId: Int) {
        try {
            movieDao.addFavoriteMovie(movieId)
            Log.d("DB_FAV_ADD", "Successful addition of favorite movie")
        } catch (error: Exception){
            Log.d("DB_FAV_ADD_ERR", "Error while trying to add a favorite movie: $error")
        }
    }

    suspend fun removeFavorite(ids: Int) {
        try {
            movieDao.removeFavMovie(ids)
            Log.d("DB_FAV_REMOVE", "Successful removal of favorite movie")
        } catch (error: Exception){
            Log.d("DB_FAV_REMOVE_ERR", "Error while trying to remove a favorite movie: $error")
        }
    }

    suspend fun isFavoriteMovie(movieId: Int): Int{
        var out = 0
        try {
            out = movieDao.isFavMovie(movieId)
            Log.d("DB_FAV_CHK", "Successful check of favorite movie")
        } catch (error: Exception){
            Log.d("DB_FAV_CHK_ERR", "Error while trying to check favorite movie: $error")
        }
        return out
    }
}