package com.bettilina.cinemanteca

import android.content.Context
import com.bettilina.cinemanteca.data.model.Movie
import com.bettilina.cinemanteca.data.model.Review
import com.bettilina.cinemanteca.data.repository.movies.CloudMovieDataStore
import com.bettilina.cinemanteca.data.repository.movies.DatabaseMovieDataStore
import com.bettilina.cinemanteca.mocks.*
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CinemantecaUnitTests {

    //arrange
    @Mock
    private lateinit var contextMock: Context

    lateinit var networkingManagerMock: NetworkingManagerMock
    lateinit var movieDataStoreFactoryMock: MovieDataStoreFactoryMock
    lateinit var movieServiceMock: MovieServiceMock
    lateinit var movieDaoMock: MovieDaoMock
    lateinit var databaseMovieDataStoreMock: DatabaseMovieDataStoreMock
    lateinit var cloudMovieDataStoreMock: CloudMovieDataStoreMock


    @Before
    fun initTests() {
        //arrange
        networkingManagerMock = NetworkingManagerMock(contextMock)
        movieServiceMock = MovieServiceMock()
        movieDaoMock = MovieDaoMock()
        movieDataStoreFactoryMock =
            MovieDataStoreFactoryMock(movieServiceMock, movieDaoMock, networkingManagerMock)
        databaseMovieDataStoreMock = DatabaseMovieDataStoreMock(movieDaoMock)
        cloudMovieDataStoreMock = CloudMovieDataStoreMock(movieServiceMock)
    }

    @Test
    fun testCloudSourceDataStoreRetrieval() {
        //arrange
        networkingManagerMock.isNetworkingAvailable = true

        //act
        val actual = movieDataStoreFactoryMock.movieDataStoreFactory

        //assert
        assert(actual is CloudMovieDataStore)
    }

    @Test
    fun testDatabaseSourceDataStoreRetrieval() {
        //arrange
        networkingManagerMock.isNetworkingAvailable = false

        //act
        val actual = movieDataStoreFactoryMock.movieDataStoreFactory

        //assert
        assert(actual is DatabaseMovieDataStore)
    }

    @Test
    fun testSaveMoviesOnDB_isSuccess() {
        //arrange
        val movies = getListOfMovies()
        movieDaoMock.moviesList = movies

        //act
        val actualResult = runBlocking {
            databaseMovieDataStoreMock.saveMovieMock(movies)

            val actual = databaseMovieDataStoreMock.getMoviesMock()

            return@runBlocking actual
        }

        //assert
        assertEquals(movies.size, actualResult.size)
    }

    @Test
    fun testSaveFavoriteMovieOnDB_isSuccess() {
        //arrange
        val movies = getListOfFavMovies()
        movieDaoMock.favMovieList = movies

        //act
        val actualResult = runBlocking {

            databaseMovieDataStoreMock.saveMovieMock(movies)

            databaseMovieDataStoreMock.addFavoriteMock(222)

            val actual = databaseMovieDataStoreMock.getFavoriteMoviesMock()

            return@runBlocking actual
        }

        //assert
        assertEquals(1, actualResult.size)
        assertEquals(222, actualResult[0].id)
    }

    @Test
    fun testRemoveFavoriteMovieOnDB_isSuccess() {
        //arrange
        movieDaoMock.favMovieList = listOf()

        //act
        val actualResult = runBlocking {
            databaseMovieDataStoreMock.removeFavoriteMock(111)

            val actual = databaseMovieDataStoreMock.getFavoriteMoviesMock()

            return@runBlocking actual
        }

        //assert
        assertEquals(0, actualResult.size)
    }

    @Test
    fun testRetrievalOfMoviesFromService_isSuccess() {
        //arrange
        movieServiceMock.movies = getListOfMovies()

        //act
        val actualResult = runBlocking {
            val actual = cloudMovieDataStoreMock.getMoviesMock()

            return@runBlocking actual
        }

        //assert
        assert(actualResult.isNotEmpty())
    }

    @Test
    fun testRetrievalOfMovieReviewFromService_isSuccess() {
        //arrange
        movieServiceMock.reviews = getListOfReviews()

        //act
        val actualResult = runBlocking {
            val actual = cloudMovieDataStoreMock.getMovieReviewsMock(111)

            return@runBlocking actual
        }

        //assert
        assert(actualResult.isNotEmpty())
    }

    private fun getListOfMovies(): List<Movie> {
        return listOf(
            Movie(
                111,
                "Title 1",
                "This is the description of movie Title 1",
                7.6.toFloat(),
                "2019-05-10",
                "some/path",
                345.66,
                344,
                "some/other/path",
                "English",
                0
            ),
            Movie(
                222,
                "Title 2",
                "This is the description of movie Title 2",
                5.6.toFloat(),
                "2015-05-09",
                "some/path",
                355.66,
                378,
                "some/other/path",
                "Spanish",
                0
            ),
            Movie(
                333,
                "Title 3",
                "This is the description of movie Title 3",
                4.5.toFloat(),
                "2015-08-12",
                "some/path",
                245.56,
                278,
                "some/other/path",
                "Spanish",
                0
            )
        )
    }


    private fun getListOfFavMovies(): List<Movie> {
        return listOf(
            Movie(
                222,
                "Title 2",
                "This is the description of movie Title 2",
                5.6.toFloat(),
                "2015-05-09",
                "some/path",
                355.66,
                378,
                "some/other/path",
                "Spanish",
                1
            )
        )
    }

    private fun getListOfReviews(): List<Review> {
        return listOf(
            Review(
                "PEPE",
                "Awesome movie"),
            Review(
                "MARTA",
                "I went with my daughter and we had so much fun!"
            )
        )
    }
}
