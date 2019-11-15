package com.bettilina.cinemanteca.data.repository

import com.bettilina.cinemanteca.data.model.Movie

interface MovieSourceRepository {

    suspend fun getMovies(): List<Movie>

    suspend fun getMoviesByPage(page: Int): List<Movie>

    suspend fun getMoviewsByVoteAvg(minVote: Int, maxVote: Int): List<Movie>
}