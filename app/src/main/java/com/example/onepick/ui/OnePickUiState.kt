package com.example.onepick.ui

/**
 * 画面のUI状態
 */
sealed interface OnePickUiState {
    data class Success(val content: String) : OnePickUiState
    data class Error(val msg: String) : OnePickUiState
    object Loading : OnePickUiState
    object LoadingNextApi : OnePickUiState
    object Initial : OnePickUiState
}