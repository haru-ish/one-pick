package com.example.onepick.network

import retrofit2.http.GET
import retrofit2.http.Query

interface ServerApiService {
    @GET("movie-title")
    suspend fun getMovieTitle(
        @Query("keyword1") keyword1: String,
        @Query("keyword2") keyword2: String,
        @Query("keyword3") keyword3: String
    ): String

    @GET("hello")
    suspend fun getHello(): String

}