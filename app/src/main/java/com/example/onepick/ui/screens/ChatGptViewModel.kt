package com.example.onepick.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.onepick.ChatGptApplication
import com.example.onepick.OnePickApplication
import com.example.onepick.data.ChatGptRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import com.example.onepick.data.ChatGptRequest
import com.example.onepick.data.Message
import com.example.onepick.ui.OnePickUiState

class ChatGptViewModel (
    private val chatGptRepository: ChatGptRepository
) : ViewModel() {

    private val sharedViewModel: SharedViewModel = OnePickApplication.getSharedViewModel()

    /**
     * ChatGptApi Retrofitサービスから映画名を取得
     */
    fun getRecommendedMovie(keyword1: String, keyword2: String, keyword3: String) {
        // coroutineを使用してAPI通信をトリガーし、適切な状態に変更する
        viewModelScope.launch {
            sharedViewModel.updateUIState(OnePickUiState.Loading)
            try {
                val prompt =
                    "「${keyword1}」「${keyword2}」「${keyword3}」の3つに当てはまる映画を一つおすすめしてほしいです。" +
                            "映画名だけ返してくれますか？"
                val request = ChatGptRequest(
                    model = "gpt-3.5-turbo",
                    messages = listOf(
                        Message(role = "system", content = "You are a helpful assistant."),
                        Message(role = "user", content = prompt)
                    )
                )
                val response = chatGptRepository.getRecommendedMovie(request)
                sharedViewModel.updateUIState(OnePickUiState.Success(response.choices[0].message.content))
                // sharedViewModel.updateUIState(OnePickUiState.LoadingNextApi)
            } catch (e: IOException) {
                sharedViewModel.updateUIState(OnePickUiState.Error(e.toString()))
            } catch (e: HttpException) {
                sharedViewModel.updateUIState(OnePickUiState.Error(e.toString()))
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
                ChatGptViewModel(
                    chatGptRepository = chatGptRepository)
            }
        }
    }
}