package com.bettilina.cinemanteca.data.repository.movies

import com.bettilina.cinemanteca.data.model.Genre
import com.bettilina.cinemanteca.data.model.Movie
import com.bettilina.cinemanteca.data.service.MovieService

class CloudMovieDataStore(private var movieService: MovieService): MovieDataStore {

    override suspend fun getMovies(apiKey: String): List<Movie> {
        return movieService.getMovies(apiKey).movieList
    }

    override suspend fun getMoviesByPage(apiKey: String, pageNumber: Int): List<Movie> {
        return movieService.getMoviesByPage(apiKey, pageNumber).movieList
    }

    override suspend fun getMoviewsByVoteAvg(apiKey: String, minVote: Int, maxVote: Int): List<Movie> {
        return movieService.getMoviesByVoteAvg(apiKey, minVote, maxVote).movieList
    }

    override suspend fun getGenres(apiKey: String): List<Genre> {
        return movieService.getGenres(apiKey).genreList
    }

}