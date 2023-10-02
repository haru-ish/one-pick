package com.example.onepick.network

import com.example.onepick.BuildConfig
import com.example.onepick.data.ChatGptRequest
import com.example.onepick.data.ChatGptResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * APIコールを作成するためのRetrofitサービスオブジェクト
 */
interface ChatGptApiService {
    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer ${BuildConfig.CHATGPT_API_KEY}"
    )
    @POST("v1/chat/completions")
    suspend fun getRecommendedMovie(@Body request: ChatGptRequest): ChatGptResponse
}

