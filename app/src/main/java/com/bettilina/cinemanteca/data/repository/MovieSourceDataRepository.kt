package com.bettilina.cinemanteca.data.repository

import com.bettilina.cinemanteca.App
import com.bettilina.cinemanteca.data.model.Genre
import com.bettilina.cinemanteca.data.model.Movie
import com.bettilina.cinemanteca.data.repository.movies.MovieDataStoreFactory

class MovieSourceDataRepository(var factory: MovieDataStoreFactory): MovieSourceRepository {

    override suspend fun getMovies(): List<Movie> {
        return factory.movieDataStoreFactory.getMovies(App.apiKey)
    }

    override suspend fun getMoviesByPage(page: Int): List<Movie> {
        return factory.movieDataStoreFactory.getMoviesByPage(App.apiKey, page)
    }

    override suspend fun getMoviewsByVoteAvg(minVote: Int, maxVote: Int): List<Movie> {
        return factory.movieDataStoreFactory.getMoviewsByVoteAvg(App.apiKey, minVote, maxVote)
    }

    override suspend fun getMoviesGenres(): List<Genre> {
        return factory.movieDataStoreFactory.getGenres(App.apiKey)
    }

    override suspend fun getMoviesBySearch(apiKey: String, txtSearch: String): List<Movie> {
        return factory.movieDataStoreFactory.getMoviesBySearch(App.apiKey, txtSearch)
    }
}