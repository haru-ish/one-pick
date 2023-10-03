package com.example.onepick.data

import com.example.onepick.network.ChatGptApiService
import com.example.onepick.network.TmdbApiService

interface TmdbRepository {

    suspend fun getMovieDetails(): TmdbResponse

    /**
     * chatGPTApiから映画名を取得するリポジトリのネットワーク実装
     */
    class NetworkTmdbRepository(
        private val tmdbApiService: TmdbApiService
    ) : TmdbRepository {
        override suspend fun getMovieDetails(): TmdbResponse =
            tmdbApiService.getMovieDetails()
    }
}