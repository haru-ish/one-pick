package com.example.onepick.data

import android.util.Log
import com.example.onepick.network.ChatGptApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface ChatGptContainer {

    val chatGptRepository: ChatGptRepository
}

class DefaultChatGptContainer : ChatGptContainer {

    private val baseUrl = "https://api.openai.com/"

    /**
     * kotlinx.serializationコンバーターを使用したRetrofitビルダーを使用して、Retrofitオブジェクトを構築
     */
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    // クラス外からアクセスする必要がないためprivateに設定
    private val retrofitService: ChatGptApiService by lazy {
        Log.d("DefaultChatGptContainer", "retrofitService is initialized")
        // retrofitServiceを初期化
        retrofit.create(ChatGptApiService::class.java)
    }

    override val chatGptRepository: ChatGptRepository by lazy {
        Log.d("DefaultChatGptContainer", "retrofitService is initialized")
        ChatGptRepository.NetworkChatGPTRepository(retrofitService)
    }

}