package com.example.onepick.data

import com.example.onepick.model.Movie
import com.example.onepick.network.ChatGptApi
import com.example.onepick.network.ChatGptApiService


/**
 * chatGptApiから映画のタイトルを取得するリポジトリ
 */
interface ChatGptRepository {
    suspend fun getRecommendedMovie(keyword1: String, keyword2: String, keyword3: String): Movie

    /**
     * chatGPTApiから映画名を取得するリポジトリのネットワーク実装
     */
    class NetworkChatGPTRepository(
        //private val chatGPTApiService: ChatGptApiService
    ) : ChatGptRepository {
        override suspend fun getRecommendedMovie(keyword1: String, keyword2: String, keyword3: String): Movie {
            return ChatGptApi.retrofitService.getRecommendedMovie(keyword1, keyword2, keyword3)
        }
    }
}

