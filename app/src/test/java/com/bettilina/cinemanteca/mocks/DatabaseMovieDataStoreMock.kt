package com.bettilina.cinemanteca.mocks

import com.bettilina.cinemanteca.data.dao.MovieDao
import com.bettilina.cinemanteca.data.model.Movie
import com.bettilina.cinemanteca.data.repository.movies.DatabaseMovieDataStore

class DatabaseMovieDataStoreMock(private val movieDao: MovieDao): DatabaseMovieDataStore(movieDao) {


    suspend fun saveMovieMock(movies: List<Movie>){
        movieDao.insertMovies(movies)
    }

    suspend fun getMoviesMock(): List<Movie> {
        return movieDao.getMovies()
    }

    suspend fun addFavoriteMock(movieId: Int) {
        movieDao.addFavoriteMovie(movieId)
    }

    suspend fun getFavoriteMoviesMock(): List<Movie> {
        return movieDao.getFavoriteMovies()
    }

    suspend fun removeFavoriteMock(ids: Int) {
        movieDao.removeFavMovie(ids)
    }
}