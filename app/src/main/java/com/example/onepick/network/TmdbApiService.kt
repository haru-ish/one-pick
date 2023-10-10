package com.example.onepick.network

import com.example.onepick.BuildConfig
import com.example.onepick.data.TmdbResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApiService {
    @GET("search/movie")
    suspend fun getMovieDetails(
        @Query("query") title: String,
        @Query("api_key") apiKey: String,
        @Query("language")language: String
    ): TmdbResponse
}