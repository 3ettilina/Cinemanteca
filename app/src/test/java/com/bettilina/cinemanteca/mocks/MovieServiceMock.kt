package com.bettilina.cinemanteca.mocks

import com.bettilina.cinemanteca.data.model.Movie
import com.bettilina.cinemanteca.data.model.Review
import com.bettilina.cinemanteca.data.service.MovieService
import com.bettilina.cinemanteca.data.service.response.MovieResponse
import com.bettilina.cinemanteca.data.service.response.ReviewResponse

class MovieServiceMock: MovieService {

    lateinit var movies: List<Movie>

    lateinit var reviews: List<Review>

    override suspend fun getMovies(apiKey: String): MovieResponse {
        return MovieResponse(movies)
    }

    override suspend fun searchMovie(apiKey: String, queryToSearch: String): MovieResponse {
        return MovieResponse(movies)
    }

    override suspend fun getMoviesByVoteAvg(
        apiKey: String,
        voteAverageGte: Int,
        voteAverageLte: Int
    ): MovieResponse {
        return MovieResponse(movies)
    }

    override suspend fun getReviews(ids: Int, apiKey: String): ReviewResponse {
        return ReviewResponse(reviews)
    }
}