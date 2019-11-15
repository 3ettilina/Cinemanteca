package com.bettilina.cinemanteca.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bettilina.cinemanteca.data.model.Movie
import com.bettilina.cinemanteca.data.service.response.MovieResponse

@Dao
interface MovieDao {

    @Query("SELECT * from Movies")
    suspend fun getMovies(): List<Movie>

    @Insert
    suspend fun insertMovies(vararg movies: Movie)

    @Insert
    suspend fun insertFavMovie(movie: Movie)

    @Delete
    suspend fun deleteMovies(vararg movies: Movie)

    @Delete
    suspend fun deleteFavMovie(movie: Movie)

}