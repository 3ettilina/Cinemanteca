package com.bettilina.cinemanteca.mocks

import com.bettilina.cinemanteca.data.dao.MovieDao
import com.bettilina.cinemanteca.data.helper.NetworkingManager
import com.bettilina.cinemanteca.data.repository.movies.MovieDataStoreFactory
import com.bettilina.cinemanteca.data.service.MovieService

class MovieDataStoreFactoryMock(
    service: MovieService,
    movieDao: MovieDao,
    networkingManager: NetworkingManager): MovieDataStoreFactory(
        service,
        movieDao,
        networkingManager
)