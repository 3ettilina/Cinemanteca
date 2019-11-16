package com.bettilina.cinemanteca.data.service.response

import com.bettilina.cinemanteca.data.model.Genre
import com.google.gson.annotations.SerializedName

data class GenreResponse (
    @SerializedName("results")
    val genreList: List<Genre>
)

