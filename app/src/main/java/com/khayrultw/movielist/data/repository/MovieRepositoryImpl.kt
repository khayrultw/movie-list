package com.khayrultw.movielist.data.repository

import com.khayrultw.movielist.data.api.MovieListApi
import com.khayrultw.movielist.data.model.Movie

class MovieRepositoryImpl private constructor(): MovieRepository {
    private val api = MovieListApi.create()

    override suspend fun getMovies(
        lang: String,
        releaseYear: String,
        page: Int,
        region: String,
        year: String,
        query: String
    ): List<Movie> {
        return api.getMovies(lang, releaseYear, page, region, year, query).results
    }

    override suspend fun getMovie(id: Int): Movie? {
        return null
    }

    companion object {
        val instance = MovieRepositoryImpl()
    }
}