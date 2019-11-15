package com.bettilina.cinemanteca.data.service.response

import com.bettilina.cinemanteca.data.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieResponse (
    @SerializedName("results")
    val movieList: List<Movie>
)