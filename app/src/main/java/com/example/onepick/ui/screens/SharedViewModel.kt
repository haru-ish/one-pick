package com.example.onepick.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.onepick.TmdbApplication
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