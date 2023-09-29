package com.example.onepick.ui.screens

import android.text.Spannable.Factory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.onepick.ChatGptApplication
import com.example.onepick.data.ChatGptRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * 画面のUI状態
 */
sealed interface OnePickUiState {
    data class Success(val title: String) : OnePickUiState
    object Error : OnePickUiState
    object Loading : OnePickUiState
    object Initial : OnePickUiState
}

class MovieViewModel(private val chatGptRepository: ChatGptRepository) : ViewModel() {

    /** 最新のリクエストのステータスを保存するミュータブルなステート */
    // MarsUiStateを初期状態で初期化し、画面が初期状態の場合にAPI通信を行わないように制御
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
                val result = chatGptRepository.getRecommendedMovie(keyword1, keyword2, keyword3)
                OnePickUiState.Success(
                    "${result.title}"
                )
            } catch (e: IOException) {
                OnePickUiState.Error
            } catch (e: HttpException) {
                OnePickUiState.Error
            }
        }
    }

    // アプリケーションコンテナによってRepositoryをViewModelに提供することで、ViewModelがRepositoryを作成するのを回避
    companion object {
        // Factoryオブジェクトはアプリケーションコンテナを使用して、chatGptRepositoryを取得
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ChatGptApplication)
                val chatGptRepository = application.container.chatGptRepository
                MovieViewModel(chatGptRepository = chatGptRepository)
            }
        }
    }
}