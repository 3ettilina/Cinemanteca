package com.bettilina.cinemanteca.data.model

data class MovieList (val page: Int,
                      val totalResults: Int,
                      val totalPages: Int,
                      val results: List<Movie>)