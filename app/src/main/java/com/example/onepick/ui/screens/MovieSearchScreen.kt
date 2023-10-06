@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.onepick.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.onepick.ui.OnePickUiState
import com.example.onepick.ui.theme.OnePickTheme

@Composable
fun MovieSearchScreen(
    onePickUiState: OnePickUiState, modifier: Modifier = Modifier
){
    when (onePickUiState) {
        is OnePickUiState.Initial -> InitialScreen(modifier = modifier.fillMaxSize())
        is OnePickUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is OnePickUiState.Success -> ResultScreen(
            onePickUiState.content, modifier = modifier.fillMaxWidth()
        )

        is OnePickUiState.Error -> ErrorScreen( onePickUiState.msg ,modifier = modifier.fillMaxSize())
        else -> {}
    }
}

@Composable
fun InitialScreen(
    onePickViewModel: OnePickViewModel = viewModel(),
    modifier: Modifier = Modifier) {

    var keyword1 by remember { mutableStateOf("") }
    var keyword2 by remember { mutableStateOf("") }
    var keyword3 by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
    ) {
        TextField(value = keyword1,
            onValueChange = { keyword1 = it},
            label = { Text(" 一つ目のキーワード")},
            modifier = Modifier.fillMaxWidth()
        )
        TextField(value = keyword2,
            onValueChange = { keyword2 = it},
            label = { Text("二つ目のキーワード")},
            modifier = Modifier.fillMaxWidth()
        )
        TextField(value = keyword3,
            onValueChange = { keyword3 = it},
            label = { Text(" 三つ目のキーワード")},
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
            onePickViewModel.getRecommendedMovie(keyword1, keyword2, keyword3)
            }
        ) {
            Text("Search")
        }

    }
}

/**
 * The home screen displaying the loading message.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Text(text = "Loading...")
//    Image(
//        modifier = modifier.size(200.dp),
//        painter = painterResource(R.drawable.loading_img),
//        contentDescription = stringResource(R.string.loading)
//    )
}

/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun ErrorScreen(msg: String, modifier: Modifier = Modifier) {
//    Column(
//        modifier = modifier,
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
//        )
//        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
//    }
    Text(text = "Error: $msg")
}

/**
 * ResultScreen displaying number of photos retrieved.
 */
@Composable
fun ResultScreen(content: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(text = content)
    }
}


@Preview(showBackground = true)
@Composable
fun OnePickAppPreview() {
    OnePickTheme {
        InitialScreen()
    }
}