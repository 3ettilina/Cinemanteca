package com.bettilina.cinemanteca.data.dao

import androidx.room.*
import com.bettilina.cinemanteca.data.model.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movies")
    suspend fun getMovies(): List<Movie>

    @Query("SELECT * FROM Movies WHERE isFavorite = 1")
    suspend fun getFavoriteMovies(): List<Movie>

    @Insert
    suspend fun insertMovies(vararg movies: Movie)

    @Query("UPDATE Movies SET isFavorite = 1 WHERE id = :movieId")
    suspend fun addFavoriteMovie(movieId: Int)

    @Delete
    suspend fun removeMovies(vararg movies: Movie)

    @Query("DELETE from Movies where id = :movieId")
    suspend fun removeFavMovie(movieId: Int): Int

    @Query("SELECT count(*) from Movies where id = :movieId ")
    suspend fun isFavMovie(movieId: Int) : Int

}