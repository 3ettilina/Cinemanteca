package com.bettilina.cinemanteca.data.repository.movies

import com.bettilina.cinemanteca.data.model.Movie
import com.bettilina.cinemanteca.data.model.Review

interface MovieDataStore {

    suspend fun getMovies(): List<Movie>

    suspend fun getMoviesByPage(pageNumber: Int = 1): List<Movie>

    suspend fun getMoviesByVoteAvg(minVote: Int, maxVote: Int): List<Movie>

    suspend fun getMoviesBySearch(txtSearch: String): List<Movie>

    suspend fun getMovieReviews(id: Int): List<Review>

    suspend fun existsMovie(id: Int): Boolean
}