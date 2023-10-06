package com.example.onepick.network

import androidx.lifecycle.ViewModel
import com.example.onepick.ui.OnePickUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<OnePickUiState>(OnePickUiState.Initial)
    val uiState: StateFlow<OnePickUiState> = _uiState.asStateFlow()

    fun updateUIState(newState: OnePickUiState) {
        _uiState.value = newState
    }
}