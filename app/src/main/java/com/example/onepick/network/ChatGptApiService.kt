package com.example.onepick.network

import com.example.onepick.model.Movie
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.onepick.BuildConfig


private const val BASE_URL = BuildConfig.CHATGPT_API_KEY

/**
 * kotlinx.serializationコンバーターを使用したRetrofitビルダーを使用して、Retrofitオブジェクトを構築
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

/**
 * APIコールを作成するためのRetrofitサービスオブジェクト
 */
interface ChatGptApiService {
    @GET("recommend")
    suspend fun getRecommendedMovie(@Query("keyword1") keyword1: String, @Query("keyword2") keyword2: String, @Query("keyword3") keyword3: String): Movie
}

/**
 * 遅延初期化されたRetrofitサービスを公開するパブリックなApiオブジェクト
 */
object ChatGptApi {
    val retrofitService: ChatGptApiService by lazy {
        // retrofitServiceを初期化
        retrofit.create(ChatGptApiService::class.java)
    }
}