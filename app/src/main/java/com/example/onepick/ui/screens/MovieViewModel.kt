package com.example.onepick.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onepick.data.ChatGptRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MovieViewModel : ViewModel() {
    /**
     * ChatGptApi Retrofitサービスから映画名を取得
     */
    fun getRecommendedMovie(keyword1: String, keyword2: String, keyword3: String) {
        // coroutineを起動
        viewModelScope.launch {
            try {
                val chatGptRepository = ChatGptRepository.NetworkChatGPTRepository()
                val result = chatGptRepository.getRecommendedMovie(keyword1, keyword2, keyword3)
            } catch (e: IOException) {

            } catch (e: HttpException) {

            }
        }



    }

}