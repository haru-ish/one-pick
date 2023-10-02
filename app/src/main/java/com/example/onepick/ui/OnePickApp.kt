package com.example.onepick.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.onepick.ui.screens.MovieViewModel
import com.example.onepick.ui.screens.MovieSearchScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnePickApp() {
    Scaffold(
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val movieViewModel: MovieViewModel = viewModel(factory = MovieViewModel.Factory)
            MovieSearchScreen(onePickUiState = movieViewModel.onePickUiState)
        }
    }
}