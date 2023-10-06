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
import com.example.onepick.data.TmdbRepository
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
                val prompt = "「${keyword1}」「${keyword2}」「${keyword3}」の3つに当てはまる映画を一つおすすめしてほしいです。" +
                        "映画名だけ返してくれますか？"
                val request = ChatGptRequest(
                    model = "gpt-3.5-turbo",
                    messages = listOf(
                        Message(role = "system", content = "You are a helpful assistant."),
                        Message(role = "user", content = prompt)
                    )
                )
                val response = chatGptRepository.getRecommendedMovie(request)

                OnePickUiState.Success(
                    response.choices[0].message.content
                )
            } catch (e: IOException) {
                OnePickUiState.Error(e.toString())
            } catch (e: HttpException) {
                OnePickUiState.Error(e.toString())
            }
        }
    }

    // アプリケーションコンテナによってRepositoryをViewModelに提供することで、ViewModelがRepositoryを作成するのを回避
    companion object {
        // Factoryオブジェクトはアプリケーションコンテナを使用して、chatGptRepositoryを取得
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as OnePickApplication)
                val chatGptRepository = application.chatGptcontainer.chatGptRepository
                val tmdbRepository = application.tmdbContainer.tmdbRepository
                OnePickViewModel(chatGptRepository = chatGptRepository, tmdbRepository = tmdbRepository)
            }
        }
    }
}