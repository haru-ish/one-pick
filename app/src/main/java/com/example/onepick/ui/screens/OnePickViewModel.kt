package com.example.onepick.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.onepick.BuildConfig
import com.example.onepick.OnePickApplication
import com.example.onepick.R
import com.example.onepick.data.ChatGptRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import com.example.onepick.data.ChatGptRequest
import com.example.onepick.data.Message
import com.example.onepick.data.TmdbRepository
import com.example.onepick.model.Movie
import com.example.onepick.ui.OnePickUiState

class OnePickViewModel(
    private val chatGptRepository: ChatGptRepository,
    private val tmdbRepository: TmdbRepository
    ) : ViewModel() {

    /** 最新のリクエストのステータスを保存するミュータブルなステート */
    // UiStateを初期状態で初期化し、画面が初期状態の場合にAPI通信を行わないように制御
    var onePickUiState: OnePickUiState by mutableStateOf(OnePickUiState.Initial)
        private set

    /**
     * ChatGptApi Retrofitサービスから映画名を取得
     */
    fun getRecommendedMovie(keyword1: String, keyword2: String, keyword3: String) {
        // coroutineを使用してAPI通信をトリガーし、適切な状態に変更する
        viewModelScope.launch {
            onePickUiState = OnePickUiState.Loading
            onePickUiState = try {
                val prompt = "「${keyword1}」「${keyword2}」「${keyword3}」の3つのワードに当てはまる映画を一つだけ教えて下さい。" +
                        "回答は以下の形で返して下さい。それ以外の情報は何も返さないで下さい。「」"
                val request = ChatGptRequest(
                    model = "gpt-3.5-turbo",
                    messages = listOf(
                        Message(role = "system", content = "You are a helpful assistant."),
                        Message(role = "user", content = prompt)
                    )
                )
                val response = chatGptRepository.getRecommendedMovie(request)

                getMovieDetails(response.choices[0].message.content)

            } catch (e: IOException) {
                OnePickUiState.Error("サーバーに接続できません。ネットワーク接続を確認してください。")
            } catch (e: HttpException) {
                OnePickUiState.Error("サーバーエラーが発生しました。時間を置いて再試行してください。")
            }
        }
    }

    private suspend fun getMovieDetails(title: String) : OnePickUiState {
        Log.d("ViewModel", title)
        return try{
            val response = tmdbRepository.getMovieDetails(title, BuildConfig.TMDB_API_KEY,"ja")
            Log.d("ViewModel", response.toString())
            if (response.totalResults == 0) {
                OnePickUiState.Error("おすすめの映画が見つかりませんでした。別のキーワードで探してみてください。")
            } else {
                OnePickUiState.Success(response.results[0])
            }
        } catch (e: IOException) {
            OnePickUiState.Error("サーバーに接続できません。ネットワーク接続を確認してください。")
        } catch (e: HttpException) {
            OnePickUiState.Error("サーバーエラーが発生しました。時間を置いて再試行してください。")
        }
    }

    fun resetAppState() {
        onePickUiState = OnePickUiState.Initial
    }

    // アプリケーションコンテナによってRepositoryをViewModelに提供することで、ViewModelがRepositoryを作成するのを回避
    companion object {
        // Factoryオブジェクトはアプリケーションコンテナを使用して、chatGptRepositoryを取得
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as OnePickApplication)
                val chatGptRepository = application.chatGptContainer.chatGptRepository
                val tmdbRepository = application.tmdbContainer.tmdbRepository
                OnePickViewModel(chatGptRepository = chatGptRepository, tmdbRepository = tmdbRepository)
            }
        }
    }
}