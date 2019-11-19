package com.bettilina.cinemanteca.mocks

import com.bettilina.cinemanteca.data.dao.MovieDao
import com.bettilina.cinemanteca.data.model.Movie

class MovieDaoMock: MovieDao {

    var isFavoriteMovie = 0

    var moviesList = listOf<Movie>()

    var favMovieList = listOf<Movie>()

    var movieExist = false

    override suspend fun getMovies(): List<Movie> {
        return moviesList
    }

    override suspend fun getFavoriteMovies(): List<Movie> {
        return favMovieList
    }

    override suspend fun insertMovies(movies: List<Movie>) {
    }

    override suspend fun removeMovies(movies: List<Movie>) {
    }

    override suspend fun addFavoriteMovie(movieId: Int) {
    }

    override suspend fun removeFavMovie(movieId: Int) {
    }

    override suspend fun updateFavMovies(movies: List<Movie>) {
    }

    override suspend fun isFavMovie(movieId: Int): Int {
        return isFavoriteMovie
    }

    override suspend fun doesMovieExist(movieId: Int): Boolean {
        return movieExist
    }
}