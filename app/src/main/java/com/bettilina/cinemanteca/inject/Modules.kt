package com.bettilina.cinemanteca.inject

import com.bettilina.cinemanteca.data.controller.RetrofitController
import com.bettilina.cinemanteca.data.dao.MovieDao
import com.bettilina.cinemanteca.data.helper.NetworkingManager
import com.bettilina.cinemanteca.data.repository.MovieSourceDataRepository
import com.bettilina.cinemanteca.data.repository.MovieSourceRepository
import com.bettilina.cinemanteca.data.repository.movies.DatabaseMovieDataStore
import com.bettilina.cinemanteca.data.repository.movies.MovieDataStoreFactory
import com.bettilina.cinemanteca.data.service.MovieService
import com.bettilina.cinemanteca.data.source.AppDatabase
import com.bettilina.cinemanteca.presentation.view.main.favorites.FavoritesViewModel
import com.bettilina.cinemanteca.presentation.view.main.home.HomeViewModel
import com.bettilina.cinemanteca.presentation.view.movie.MovieGenresViewModel
import com.bettilina.cinemanteca.presentation.view.movie.ReviewsViewModel
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

var networkingModule = module{

    single { NetworkingManager(get()) }
    single<GsonConverterFactory> {
        GsonConverterFactory.create(
            GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
        )
    }

    single { RetrofitController(get()) }
    single<Retrofit> { get<RetrofitController>().initRetrofit() }
    single<MovieService> { get<Retrofit>().create(MovieService::class.java)}
}

var databaseModule = module{
    single { AppDatabase.getInstance(get()).movieDao() }
}

var movieModule = module {
    single{ MovieDataStoreFactory(get(), get(), get())}
    single<MovieSourceRepository> { MovieSourceDataRepository(get()) }
    single<DatabaseMovieDataStore> { DatabaseMovieDataStore(get()) }

    viewModel{ HomeViewModel(get()) }
    viewModel{ FavoritesViewModel(get()) }
    viewModel{ MovieGenresViewModel(get()) }
    viewModel{ ReviewsViewModel(get()) }
}