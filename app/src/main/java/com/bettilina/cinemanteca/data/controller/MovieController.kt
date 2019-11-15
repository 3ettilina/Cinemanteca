package com.bettilina.cinemanteca.data.controller

import com.bettilina.cinemanteca.App
import com.bettilina.cinemanteca.data.service.MovieService

class MovieController(
    private val movieService: MovieService,
    private val retrofitController: RetrofitController
) {

    suspend fun getMoviesByPage(page: Int = 1){
        movieService.getMoviesByPage(App.apiKey, page)
    }

    suspend fun searchMovie(query: String){
        movieService.searchMovie(App.apiKey, query)
    }

    suspend fun getMoviesByVoteAvg(minVote: Int, maxVote: Int){
        movieService.getMoviesByVoteAvg(
            App.apiKey,
            minVote,
            maxVote
        )
    }
}