package com.bettilina.cinemanteca.data.repository

import com.bettilina.cinemanteca.data.model.Movie
import com.bettilina.cinemanteca.data.model.Review
import com.bettilina.cinemanteca.data.repository.movies.MovieDataStoreFactory

class MovieSourceDataRepository(var factory: MovieDataStoreFactory): MovieSourceRepository {

    override suspend fun getMovies(): List<Movie> {
        return factory.movieDataStoreFactory.getMovies()
    }

    override suspend fun getMoviesByVoteAvg(minVote: Int, maxVote: Int): List<Movie> {
        return factory.movieDataStoreFactory.getMoviesByVoteAvg(minVote, maxVote)
    }

    override suspend fun getMoviesBySearch(txtSearch: String): List<Movie> {
        return factory.movieDataStoreFactory.getMoviesBySearch(txtSearch)
    }

    override suspend fun getMovieReviews(id: Int): List<Review> {
        return factory.movieDataStoreFactory.getMovieReviews(id)
    }

    override suspend fun existsMovie(id: Int): Boolean {
        return factory.movieDataStoreFactory.existsMovie(id)
    }
}