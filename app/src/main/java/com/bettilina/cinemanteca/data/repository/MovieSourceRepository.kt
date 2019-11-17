package com.bettilina.cinemanteca.data.repository

import com.bettilina.cinemanteca.data.model.Movie
import com.bettilina.cinemanteca.data.model.Review

interface MovieSourceRepository {

    suspend fun getMovies(): List<Movie>

    suspend fun getMoviesByPage(page: Int): List<Movie>

    suspend fun getMoviesByVoteAvg(minVote: Int, maxVote: Int): List<Movie>

    suspend fun getMoviesBySearch(txtSearch: String): List<Movie>

    suspend fun getMovieReviews(id: Int): List<Review>
}