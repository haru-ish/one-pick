package com.example.onepick.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.onepick.R
import com.example.onepick.ui.screens.ChatGptViewModel
import com.example.onepick.ui.screens.MovieSearchScreen
import com.example.onepick.ui.screens.SharedViewModel
import com.example.onepick.ui.screens.TmdbViewModel
//import androidx.lifecycle.viewmodel.compose.viewModels

//import androidx.activity.compose.viewModels

import androidx.activity.viewModels


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnePickApp() {
    val sharedViewModel: SharedViewModel = viewModel()

    Scaffold(
         topBar = { OnePickTopAppBar() }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val chatGptViewModel: ChatGptViewModel = viewModel(factory = ChatGptViewModel.Factory)
            val tmdbViewModel: TmdbViewModel = viewModel(factory = TmdbViewModel.Factory)

            MovieSearchScreen(
                chatGptViewModel = chatGptViewModel,
                tmdbViewModel = tmdbViewModel,
                sharedViewModel = sharedViewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnePickTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineSmall
            )
        },
        modifier = modifier
    )
}