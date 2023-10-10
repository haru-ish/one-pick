package com.example.onepick.ui

import com.example.onepick.model.Movie

/**
 * 画面のUI状態
 */
sealed interface OnePickUiState {
    data class Success(val content: Movie) : OnePickUiState
    data class Error(val msg: String) : OnePickUiState
    object Loading : OnePickUiState
    object Initial : OnePickUiState
}