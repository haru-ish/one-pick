package com.example.onepick.data

import com.example.onepick.network.ServerApiService

interface ServerRepository {

    suspend fun getMovieTitle(keyword1: String, keyword2: String, keyword3: String): String

    /**
     * webserverにhttpApiリクエストを行い、映画のタイトルを取得するリポジトリのネットワーク実装
     */
    class NetworkServerRepository(
        private val serverApiService: ServerApiService
    ) : ServerRepository {
        override suspend fun getMovieTitle(keyword1: String, keyword2: String, keyword3: String): String =
            serverApiService.getMovieTitle(keyword1, keyword2, keyword3)
    }
}