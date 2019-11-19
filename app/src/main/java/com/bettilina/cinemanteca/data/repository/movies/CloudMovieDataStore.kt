package com.bettilina.cinemanteca.data.repository.movies

import com.bettilina.cinemanteca.data.model.Movie
import com.bettilina.cinemanteca.data.model.Review
import com.bettilina.cinemanteca.data.service.MovieService

class CloudMovieDataStore(private var movieService: MovieService,
                          private val apiKey: String): MovieDataStore {

    override suspend fun getMovies(): List<Movie> {
        return movieService.getMovies(apiKey).movieList
    }

    override suspend fun getMoviesByPage(pageNumber: Int): List<Movie> {
        return movieService.getMoviesByPage(apiKey, pageNumber).movieList
    }

    override suspend fun getMoviesByVoteAvg(minVote: Int, maxVote: Int): List<Movie> {
        return movieService.getMoviesByVoteAvg(apiKey, minVote, maxVote).movieList
    }

    override suspend fun getMoviesBySearch(txtSearch: String): List<Movie> {
        return movieService.searchMovie(apiKey, txtSearch).movieList
    }

    override suspend fun getMovieReviews(id: Int): List<Review> {
        return  movieService.getReviews(id,apiKey).reviewList
    }

    override suspend fun existsMovie(id: Int): Boolean {
        return false
    }
}