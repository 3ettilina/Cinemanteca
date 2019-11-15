package com.bettilina.cinemanteca.data.repository.movies

import com.bettilina.cinemanteca.data.dao.MovieDao
import com.bettilina.cinemanteca.data.helper.NetworkingManager
import com.bettilina.cinemanteca.data.service.MovieService

@Suppress("UNUSED_PARAMETER")
open class MovieDataStoreFactory(
    var service: MovieService,
    var movieDao: MovieDao,
    var networkingManager: NetworkingManager
) {

    open var movieDataStoreFactory: MovieDataStore
    get() {
        return createDataSourceFactory()
    }
    set(value) {}

    private fun createDataSourceFactory() =
        if(networkingManager.isNetworkOnline())
            CloudMovieDataStore(service)
        else
            DatabaseMovieDataStore(movieDao)
}