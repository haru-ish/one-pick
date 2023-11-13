package com.example.onepick.data

import com.example.onepick.network.ServerApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

interface ServerContainer {
    val serverRepository: ServerRepository
}

class DefaultServerContainer : ServerContainer {

    private val baseUrl = "http://192.168.10.124:8080/"

    /**
     * kotlinx.serializationコンバーターを使用したRetrofitビルダーを使用して、Retrofitオブジェクトを構築
     */
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        //.addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    // クラス外からアクセスする必要がないためprivateに設定
    private val retrofitService: ServerApiService by lazy {
        // retrofitServiceを初期化
        retrofit.create(ServerApiService::class.java)
    }

    override val serverRepository: ServerRepository by lazy {
        ServerRepository.NetworkServerRepository(retrofitService)
    }
}