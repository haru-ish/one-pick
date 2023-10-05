package com.example.onepick.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.onepick.OnePickApplication
import com.example.onepick.TmdbApplication
import com.example.onepick.data.ChatGptResponse
import com.example.onepick.data.TmdbRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class TmdbViewModel (
    private val tmdbRepository: TmdbRepository
) : ViewModel() {

    private val sharedViewModel: SharedViewModel = OnePickApplication.getSharedViewModel()

//    /** 最新のリクエストのステータスを保存するミュータブルなステート */
//    // OneUiStateを初期状態で初期化し、画面が初期状態の場合にAPI通信を行わないように制御
//    var onePickUiState: OnePickUiState by mutableStateOf(OnePickUiState.Initial)
//        private set

    private val _chatGptResponse = MutableStateFlow<ChatGptResponse?>(null)
    val chatGptResponse: StateFlow<ChatGptResponse?> = _chatGptResponse.asStateFlow()

    // データを更新するファンクション
    fun updateChatGptResponse(chatGptResponse: ChatGptResponse) {
        _chatGptResponse.value = chatGptResponse
    }

//    fun getMovieInformation() {
//        viewModelScope.launch {
//            chatGptResponse.collect { response ->
//                if (response != null) {
//                    val movieName = response.choices[0].message.content
//                }
//
//            }
//        }
//
//    }
//    private suspend fun getMovieInformation() {
//
//    }

    // アプリケーションコンテナによってRepositoryをViewModelに提供することで、ViewModelがRepositoryを作成するのを回避
    companion object {
        // Factoryオブジェクトはアプリケーションコンテナを使用して、tmdbRepositoryを取得
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TmdbApplication)
                val tmdbRepository = application.container.tmdbRepository
                TmdbViewModel(tmdbRepository = tmdbRepository)
            }
        }
    }

}