package com.example.onepick.network

import com.example.onepick.BuildConfig
import com.example.onepick.data.TmdbResponse
import org.intellij.lang.annotations.Language
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface TmdbApiService {
//    @Headers(
//        "accept: application/json",
//        "Authorization: Bearer ${BuildConfig.TMDB_API_KEY}"
//    )
    @GET("search/movie")
    suspend fun getMovieDetails(
        @Query("query") title: String,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language")language: String = "ja"
    ): TmdbResponse
}

// "search/movie?query=Batman?language=ja