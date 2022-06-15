package com.khayrultw.movielist.data.repository

import com.khayrultw.movielist.data.model.Movie

interface MovieRepository {
    suspend fun getMovies(
        lang: String,
        releaseYear: String,
        page: Int,
        region: String,
        year: String,
        query: String
    ): List<Movie>

    suspend fun getMovie(id: Int): Movie?
}