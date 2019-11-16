package com.bettilina.cinemanteca.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.threeten.bp.ZonedDateTime

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey val id: Int,
    val title: String,
    @SerializedName("overview")
    val description: String,
    val voteAverage: Float,
    val releaseDate: String,
    val posterPath: String,
    val popularity: Double,
    val voteCount: Int,
    val backdropPath: String,
    @SerializedName("original_language")
        val language: String

)
