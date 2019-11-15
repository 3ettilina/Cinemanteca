package com.bettilina.cinemanteca.data.controller

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitController (
    private val gsonConverterFactory: GsonConverterFactory){

    private val httpClient
        get() = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()

    fun initRetrofit() =
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(gsonConverterFactory)
            .client(httpClient)
            .build()
}