package com.khayrultw.movielist.data.api

import com.khayrultw.movielist.data.model.Movie
import com.khayrultw.movielist.data.model.MovieResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Query

interface MovieListApi {

    @POST(Routes.GET_MOVIES)
    suspend fun getMovies(
        @Query("language") lang: String,
        @Query("primary_release_year") releaseYear: String,
        @Query("page") page: Int,
        @Query("region") region: String,
        @Query("year") year: String,
        @Query("query") query: String
    ): MovieResponse

    companion object {
        private const val BASE_URL = "https://api.m3o.com"
        private const val BEARER_TOKEN = "ZTc5NDZjMTQtNzQ4ZS00Mjc2LTgzNDAtMzAyNTI3NTFmOGVi"
        const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/original"

        var instance: MovieListApi? = null

        fun create(): MovieListApi {
            if(instance == null) {
                val interceptor = OkHttpClient.Builder().addInterceptor(Interceptor {
                    val request = it.request().newBuilder().addHeader("Authorization", "Bearer $BEARER_TOKEN").build()
                    it.proceed(request)
                }).build()

                instance = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(interceptor)
                    .build()
                    .create(MovieListApi::class.java)
            }

            return instance as MovieListApi
        }
    }
}