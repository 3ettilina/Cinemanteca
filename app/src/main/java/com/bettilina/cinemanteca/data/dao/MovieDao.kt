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
    suspend fun insertMovies(movies: List<Movie>)

    @Delete
    suspend fun removeMovies(movies: List<Movie>)

    @Query("UPDATE Movies SET isFavorite = 1 WHERE id = :movieId")
    suspend fun addFavoriteMovie(movieId: Int)

    @Query("UPDATE Movies SET isFavorite = 0 where id = :movieId")
    suspend fun removeFavMovie(movieId: Int): Int

    @Update
    suspend fun updateFavMovies(movies: List<Movie>)

    @Query("SELECT isFavorite from Movies where id = :movieId AND isFavorite = 1 ")
    suspend fun isFavMovie(movieId: Int) : Int

    @Query("SELECT count(*)>0 from Movies where id = :movieId ")
    suspend fun existMovie(movieId: Int) : Boolean
}