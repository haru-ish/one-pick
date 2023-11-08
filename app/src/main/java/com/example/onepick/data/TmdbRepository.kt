package com.example.onepick.data

import com.example.onepick.network.TmdbApiService

/**
 * tmdbApiから映画の詳細を取得するリポジトリ
 */
interface TmdbRepository {

    suspend fun getMovieDetails(title: String, language: String): TmdbResponse

    /**
     * tmdbApiから映画の詳細を取得するリポジトリのネットワーク実装
     */
    class NetworkTmdbRepository(
        private val tmdbApiService: TmdbApiService
    ) : TmdbRepository {
        override suspend fun getMovieDetails(title: String, language: String): TmdbResponse =
            tmdbApiService.getMovieDetails(title, language)
    }
}