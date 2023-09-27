package com.example.onepick.ui.screens

import android.text.Spannable.Factory
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

class MovieViewModel(private val chatGptRepository: ChatGptRepository) : ViewModel() {
    /**
     * ChatGptApi Retrofitサービスから映画名を取得
     */
    fun getRecommendedMovie(keyword1: String, keyword2: String, keyword3: String) {
        // coroutineを起動
        viewModelScope.launch {
            try {
                val result = chatGptRepository.getRecommendedMovie(keyword1, keyword2, keyword3)
            } catch (e: IOException) {

            } catch (e: HttpException) {

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