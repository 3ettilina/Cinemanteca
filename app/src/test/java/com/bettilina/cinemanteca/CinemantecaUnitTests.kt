package com.bettilina.cinemanteca

import android.content.Context
import com.bettilina.cinemanteca.mocks.MovieDataStoreFactoryMock
import com.bettilina.cinemanteca.mocks.MovieServiceMock
import com.bettilina.cinemanteca.mocks.NetworkingManagerMock
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
//import org.mockito.Mock
//import org.mockito.runners.MockitoJUnitRunner

//@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {

    //arrange
    // Declare mock objects needed
    //@Mock
    private lateinit var contextMock: Context

    lateinit var networkingManagerMock: NetworkingManagerMock
    lateinit var movieDataStoreFactoryMock: MovieDataStoreFactoryMock
    lateinit var movieServiceMock: MovieServiceMock


    @Before
    fun initTests(){
        //arrange
        //Initialize mock objects
        networkingManagerMock = NetworkingManagerMock(contextMock)
        movieServiceMock = MovieServiceMock()
        //movieDataStoreFactoryMock = MovieDataStoreFactoryMock()
    }

    @Test
    fun testCloudSourceDataStoreRetrieval(){
        //arrange
        // set networkManager connectivity to true
        networkingManagerMock.isNetworkingAvailable = true

        //act
        // Call MovieDataStoreFactory mock method to get DataStore


        //assert
        // Save actual value when retrieving the DataStore
        // Save expected value for that retrieval (CloudMovieDataStore)
        // Assert equals
    }

    @Test
    fun testDatabaseSourceDataStoreRetrieval(){
        //arrange
        // set networkManager connectivity to false

        //act
        // Call MovieDataStoreFactory mock method to get DataStore

        //assert
        // Save actual value when retrieving the DataStore
        // Save expected value for that retrieval (DatabaseMovieDataStore)
        // Assert equals
    }

    @Test
    fun testSaveMoviesOnDB_isSuccess(){
        //arrange
        // create a private method to return a list with movie mock objects

        //act
        // Call DatabaseMovieDataStore mock to add movies

        //assert
        // Retrieve movies from DB and check whether the size of the list,
        // equals the size of the list added.
    }

    @Test
    fun testSaveFavoriteMovieOnDB_isSuccess(){
        //arrange
        // use last method that returns a list of movies and save it on the DB
        // create a variable with the id of the movie to add to favorite

        //act
        // Call DatabaseMovieDataStore mock method to add movie to favs

        //assert
        // Save actual value when retrieving Fav movies
        // Save expected value for that retrieval
        // Assert
    }

    @Test
    fun testSaveFavoriteMovieOnDB_fails(){
        //arrange
        // use last method that returns a list of movies and save it on the DB
        // create a variable with the id of the movie to add to favorite, but use one
        // that doesn't exist on the db

        //act
        // Call DatabaseMovieDataStore mock method to add movie to favs

        //assert
        // Save actual value when retrieving Fav movies
        // Save expected value for that retrieval
        // Assert
    }

    @Test
    fun testRemovalOfFavoriteMovieOnDB_isSuccess(){
        //arrange
        // use last method that returns a list of movies and save it on the DB
        // create a variable with the id of the movie to remove

        //act
        // Call DatabaseMovieDataStore mock method to remove movie from DB

        //assert
        // Save actual value when retrieving Fav movies
        // Save expected value for that retrieval
        // Assert
    }

    @Test
    fun testRemovalOfFavoriteMovieOnDB_fails(){
        //arrange
        // use last method that returns a list of movies and save it on the DB
        // create a variable with the id of the movie to remove

        //act
        // Call DatabaseMovieDataStore mock method to remove movie from DB

        //assert
        // Save actual value when retrieving Fav movies
        // Save expected value for that retrieval
        // Assert
    }

    @Test
    fun testRetrievalOfMoviesFromService_isSuccess(){
        //act
        // Call CloudMoviesDataStore mock method to retrieve list of movies

        //assert
        // assert the list is not empty
    }

}
