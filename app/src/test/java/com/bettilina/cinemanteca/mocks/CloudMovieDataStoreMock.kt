package com.bettilina.cinemanteca.mocks

import com.bettilina.cinemanteca.App
import com.bettilina.cinemanteca.data.model.Movie
import com.bettilina.cinemanteca.data.model.Review
import com.bettilina.cinemanteca.data.repository.movies.CloudMovieDataStore
import com.bettilina.cinemanteca.data.service.MovieService

class CloudMovieDataStoreMock(private val movieService: MovieService): CloudMovieDataStore(movieService, App.apiKey) {

    suspend fun getMoviesMock(): List<Movie> {
        return movieService.getMovies(App.apiKey).movieList
    }

    suspend fun getMovieReviewsMock(id: Int): List<Review> {
        return  movieService.getReviews(id, App.apiKey).reviewList
    }
}