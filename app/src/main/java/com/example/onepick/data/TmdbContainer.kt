package com.example.onepick.data

import com.example.onepick.network.TmdbApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface TmdbContainer {
    val tmdbRepository: TmdbRepository
}

class DefaultTmdbContainer : TmdbContainer {

    private val baseUrl = "https://api.themoviedb.org/3/"

    /**
     * kotlinx.serializationコンバーターを使用したRetrofitビルダーを使用して、Retrofitオブジェクトを構築
     */
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    // クラス外からアクセスする必要がないためprivateに設定
    private val retrofitService: TmdbApiService by lazy {
        // retrofitServiceを初期化
        retrofit.create(TmdbApiService::class.java)
    }

    override val tmdbRepository: TmdbRepository by lazy {
        TmdbRepository.NetworkTmdbRepository(retrofitService)
    }
}