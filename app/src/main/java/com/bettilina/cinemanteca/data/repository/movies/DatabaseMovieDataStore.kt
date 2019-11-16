package com.bettilina.cinemanteca.data.repository.movies

import com.bettilina.cinemanteca.data.dao.MovieDao
import com.bettilina.cinemanteca.data.model.Genre
import com.bettilina.cinemanteca.data.model.Movie
import com.bettilina.cinemanteca.data.service.response.MovieResponse

class DatabaseMovieDataStore (private val movieDao: MovieDao): MovieDataStore{

    override suspend fun getMovies(apiKey: String): List<Movie> {
        return movieDao.getMovies()
    }

    //If the user doesn't have internet connection, won't be able to load more movies
    //on the scroll view.
    override suspend fun getMoviesByPage(apiKey: String, pageNumber: Int): List<Movie> {
        return MovieResponse(listOf()).movieList
    }

    //TODO: check how to create the query on the MovieDao class to return
    // movies by VoteAvg
    override suspend fun getMoviewsByVoteAvg(
        apiKey: String,
        minVote: Int,
        maxVote: Int
    ): List<Movie> {
        return movieDao.getMovies()
    }

    override suspend fun getGenres(apiKey: String): List<Genre> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}