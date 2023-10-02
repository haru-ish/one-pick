package com.example.onepick.data

import com.example.onepick.network.ChatGptApiService

/**
 * chatGptApiから映画のタイトルを取得するリポジトリ
 */
interface ChatGptRepository {
    suspend fun getRecommendedMovie(request: ChatGptRequest): ChatGptResponse

    /**
     * chatGPTApiから映画名を取得するリポジトリのネットワーク実装
     */
    class NetworkChatGPTRepository(
        private val chatGptApiService: ChatGptApiService
    ) : ChatGptRepository {
        override suspend fun getRecommendedMovie(request: ChatGptRequest): ChatGptResponse =
            chatGptApiService.getRecommendedMovie(request)
    }
}

