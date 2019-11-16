package com.bettilina.cinemanteca.data.service.response

import com.bettilina.cinemanteca.data.model.Movie
import com.bettilina.cinemanteca.data.model.Review
import com.google.gson.annotations.SerializedName

data class ReviewResponse (
    @SerializedName("results")
    val reviewList: List<Review>
)