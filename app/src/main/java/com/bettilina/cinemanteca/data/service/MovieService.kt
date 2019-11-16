package com.bettilina.cinemanteca.data.service

import com.bettilina.cinemanteca.data.service.response.GenreResponse
import com.bettilina.cinemanteca.data.service.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("discover/movie/")
    suspend fun getMovies(@Query("api_key") apiKey: String): MovieResponse

    @GET("discover/movie/")
    suspend fun getMoviesByPage(@Query("api_key") apiKey: String,
                                @Query("page") pageNumber: Int): MovieResponse

    @GET("search/movie/")
    suspend fun searchMovie(@Query("api_key") apiKey: String,
                            @Query("query") queryToSearch: String): MovieResponse

    @GET("discover/movie/")
    suspend fun getMoviesByVoteAvg(@Query("api_key") apiKey: String,
                                   @Query("vote_average.gte") voteAverageGte: Int,
                                   @Query("vote_average.lte") voteAverageLte: Int): MovieResponse

    @GET("discover/genre/movie/list")
    suspend fun getGenres(@Query("api_key") apiKey: String): GenreResponse
}