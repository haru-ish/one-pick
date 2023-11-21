package com.example.onepick.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.onepick.OnePickApplication
import com.example.onepick.data.ChatGptRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import com.example.onepick.data.ChatGptRequest
import com.example.onepick.data.Message
import com.example.onepick.data.ServerRepository
import com.example.onepick.data.TmdbRepository
import com.example.onepick.ui.OnePickUiState

class OnePickViewModel(
    private val chatGptRepository: ChatGptRepository,
    private val tmdbRepository: TmdbRepository,
    private val serverRepository: ServerRepository
    ) : ViewModel() {

    /** 最新のリクエストのステータスを保存するミュータブルなステート */
    // UIStateを初期状態で初期化し、画面が初期状態の場合にAPI通信を行わないように制御
    var onePickUiState: OnePickUiState by mutableStateOf(OnePickUiState.Initial)
        private set

    /**
     * ChatGptApi Retrofitサービスから映画名を取得
     */
    fun getRecommendedMovie(keyword1: String, keyword2: String, keyword3: String) {
        // coroutineを使用してAPI通信をトリガーし、適切な状態に変更する
        viewModelScope.launch {
            // UIStateをInitialからLoadingに変更
            onePickUiState = OnePickUiState.Loading
            // API通信が成功すればSuccessを、失敗すればErrorを返す
            onePickUiState = try {
                val prompt = "「${keyword1}」「${keyword2}」「${keyword3}」の3つのワードに当てはまる映画を一つだけ教えて下さい。ランダムにお願いします。" +
                        "回答は以下の形で返して下さい。それ以外の情報は何も返さないで下さい。「」"
                val request = ChatGptRequest(
                    model = "gpt-3.5-turbo",
                    messages = listOf(
                        Message(role = "system", content = "You are a helpful assistant."),
                        Message(role = "user", content = prompt)
                    )
                )
                // ChatGptApiとRepositoryクラスを介して通信
                val response = chatGptRepository.getRecommendedMovie(request)
                // ChatGptApiから回答が返ってきたら、以下のファンクションを呼び出す
                getMovieDetails(response.choices[0].message.content)
            } catch (e: IOException) {
                OnePickUiState.Error("サーバーに接続できません。\nネットワーク接続を確認してください。")
            } catch (e: HttpException) {
                OnePickUiState.Error("サーバーエラーが発生しました。\n時間を置いて再試行してください。")
            }
        }
    }

    /**
     * httpApi Retrofitサービスから映画名を取得
     */
    fun getMovieTitle(keyword1: String, keyword2: String, keyword3: String) {
        // coroutineを使用してAPI通信をトリガーし、適切な状態に変更する
        viewModelScope.launch {
            // UIStateをInitialからLoadingに変更
            onePickUiState = OnePickUiState.Loading
            // API通信が成功すればSuccessを、失敗すればErrorを返す
            onePickUiState = try {
                // ChatGptApiとRepositoryクラスを介して通信
                val response = serverRepository.getMovieTitle(keyword1, keyword2, keyword3)
                // ChatGptApiから回答が返ってきたら、以下のファンクションを呼び出す
                getMovieDetails(response)
            } catch (e: IOException) {
                OnePickUiState.Error("サーバーに接続できません。\nネットワーク接続を確認してください。")
            } catch (e: HttpException) {
                OnePickUiState.Error("サーバーエラーが発生しました。\n時間を置いて再試行してください。")
            }
        }

    }

    /**
     *TmdbApi Retrofitサービスから映画の詳細を取得
     */
    private suspend fun getMovieDetails(title: String) : OnePickUiState {
        return try{
            // TmdbApiとRepositoryクラスを介して通信
            val response = tmdbRepository.getMovieDetails(title,"ja")
            // TmdbApiから映画の詳細が返ってこなかったら、エラーを返す
            if (response.totalResults == 0) {
                OnePickUiState.Error("おすすめの映画が見つかりませんでした。\n別のキーワードで探してみてください。")
            } else {
                // 映画の詳細をUI(ResultScreen)で表示するため、Successを返す
                OnePickUiState.Success(response.results[0])
            }
        } catch (e: IOException) {
            OnePickUiState.Error("サーバーに接続できません。\nネットワーク接続を確認してください。")
        } catch (e: HttpException) {
            OnePickUiState.Error("サーバーエラーが発生しました。\n時間を置いて再試行してください。")
        }
    }

    /**
     *UIStateを初期状態にリセット
     */
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
                val serverRepository = application.serverContainer.serverRepository
                OnePickViewModel(chatGptRepository = chatGptRepository, tmdbRepository = tmdbRepository, serverRepository = serverRepository)
            }
        }
    }
}