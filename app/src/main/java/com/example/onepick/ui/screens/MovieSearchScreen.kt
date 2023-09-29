package com.example.onepick.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MovieSearchScreen(
    onePickUiState: OnePickUiState, modifier: Modifier = Modifier
){
    if(onePickUiState == OnePickUiState.Initial) {
        InitialScreen()
    } else {
        // to-do
    }


}

@Composable
fun InitialScreen(modifier: Modifier = Modifier) {

}