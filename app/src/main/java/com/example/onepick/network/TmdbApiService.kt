package com.example.onepick.network

import com.example.onepick.BuildConfig
import com.example.onepick.data.TmdbResponse
import retrofit2.http.GET
import retrofit2.http.Headers

interface TmdbApiService {
    @Headers(
        "accept: application/json",
        "Authorization: Bearer ${BuildConfig.TMDB_API_KEY}"
    )
    @GET("search/movie?query=Batman?language=ja")
    suspend fun getMovieDetails(): TmdbResponse
}