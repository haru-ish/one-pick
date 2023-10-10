@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.onepick.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.onepick.data.ChatGptRepository
import com.example.onepick.data.TmdbRepository
import com.example.onepick.model.Movie
import com.example.onepick.ui.OnePickUiState
import com.example.onepick.ui.theme.OnePickTheme

@Composable
fun MovieSearchScreen(
    onePickUiState: OnePickUiState,
    onePickViewModel: OnePickViewModel,
    modifier: Modifier = Modifier
){
    when (onePickUiState) {
        is OnePickUiState.Initial -> InitialScreen(onePickViewModel, modifier = modifier.fillMaxSize())
        is OnePickUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is OnePickUiState.Success -> ResultScreen(
            onePickUiState.content, onePickViewModel, modifier = modifier.fillMaxWidth()
        )

        is OnePickUiState.Error -> ErrorScreen( onePickUiState.msg, onePickViewModel, modifier = modifier.fillMaxSize())
        else -> { }
    }
}

@Composable
fun InitialScreen(
    onePickViewModel: OnePickViewModel,
    modifier: Modifier = Modifier) {

    var keyword1 by remember { mutableStateOf("") }
    var keyword2 by remember { mutableStateOf("") }
    var keyword3 by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text ="今の気分にぴったりな映画をおすすめします。\n" +
                    "キーワードを最大3つまで入力してください。",
            style = typography.titleSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = keyword1,
            singleLine = true,
            shape = shapes.large,
            onValueChange = { keyword1 = it},
            label = { Text("キーワード1")},
            leadingIcon = { Icon( Icons.Filled.Search, contentDescription = null ) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            // colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
            modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth()
        )
        // Spacer(modifier = Modifier.height(150.dp))
        OutlinedTextField(value = keyword2,
            singleLine = true,
            shape = shapes.large,
            onValueChange = { keyword2 = it},
            label = { Text("キーワード2")},
            leadingIcon = { Icon( Icons.Filled.Search, contentDescription = null ) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth()
        )
        // Spacer(modifier = Modifier.height(150.dp))
        OutlinedTextField(value = keyword3,
            singleLine = true,
            shape = shapes.large,
            onValueChange = { keyword3 = it},
            label = { Text("キーワード3")},
            leadingIcon = { Icon( Icons.Filled.Search, contentDescription = null ) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth()
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onePickViewModel.getRecommendedMovie(keyword1, keyword2, keyword3)
            }
        ) {
            Text(
                "Search",
                fontSize = 16.sp
            )
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
fun ErrorScreen(msg: String,
                onePickViewModel: OnePickViewModel,
                modifier: Modifier = Modifier
) {
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
    Button(
        onClick = {
            onePickViewModel.resetAppState()
        }
    ) {
        Text("検索に戻る")
    }
}

/**
 * ResultScreen displaying number of photos retrieved.
 */
@Composable
fun ResultScreen(
    content: Movie,
    onePickViewModel: OnePickViewModel,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/w500/${content.posterPath}")
                    .crossfade(true)
                    .build(),
                // placeholder = painterResource(R.drawable.placeholder),
                contentDescription = null,
                //contentScale = ContentScale.Crop,
                //modifier = Modifier.clip(CircleShape)
            )
            Text(text = "「${content.title!!}」")
            Text(text = "${content.releaseDate!!} 公開")
            Text(text = "あらすじ: n${content.overview!!}")

            Button(
                onClick = {
                    onePickViewModel.resetAppState()
                }
            ) {
                Text("検索に戻る")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun OnePickAppPreview() {
    // val onePickViewModel: OnePickViewModel = viewModel(factory = OnePickViewModel.Factory)
    OnePickTheme {
       // InitialScreen( onePickViewModel = onePickViewModel )
    }
}