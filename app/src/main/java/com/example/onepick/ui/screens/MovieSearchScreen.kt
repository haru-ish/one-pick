@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.onepick.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.onepick.R
import com.example.onepick.data.ChatGptRepository
import com.example.onepick.data.TmdbRepository
import com.example.onepick.model.Movie
import com.example.onepick.model.genres
import com.example.onepick.ui.OnePickUiState
import com.example.onepick.ui.theme.OnePickTheme
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun MovieSearchScreen(
    onePickViewModel: OnePickViewModel,
    modifier: Modifier = Modifier
){
    // ViewModelからデータを受け取る
    //val uiState by remember { mutableStateOf(onePickViewModel.onePickUiState) }
    val uiState = onePickViewModel.onePickUiState

    when (uiState) {
        is OnePickUiState.Initial -> InitialScreen(onePickViewModel, modifier = modifier.fillMaxSize())
        is OnePickUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is OnePickUiState.Success -> ResultScreen(
            uiState.content, onePickViewModel, modifier = modifier.fillMaxWidth()
        )
        is OnePickUiState.Error -> ErrorScreen( uiState.msg, onePickViewModel, modifier = modifier.fillMaxSize())
        else -> { }
    }
}

@Composable
fun InitialScreen(
    onePickViewModel: OnePickViewModel,
    modifier: Modifier = Modifier
) {
    var keyword1 by remember { mutableStateOf("") }
    var keyword2 by remember { mutableStateOf("") }
    var keyword3 by remember { mutableStateOf("") }

    var isNoInput by remember { mutableStateOf(false) }

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
        OutlinedTextFieldWithCounter(
            value = keyword1,
            onValueChange = { keyword1 = it },
            labelResId = if (isNoInput) R.string.label_error else R.string.label_1,
            imeAction = ImeAction.Next,
            isError = isNoInput
        )
        OutlinedTextFieldWithCounter(
            value = keyword2,
            onValueChange = { keyword2 = it },
            labelResId = R.string.label_2,
            imeAction = ImeAction.Next,
            isError = false
        )
        OutlinedTextFieldWithCounter(
            value = keyword3,
            onValueChange = { keyword3 = it },
            labelResId = R.string.label_3,
            imeAction = ImeAction.Done,
            isError = false
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if(keyword1.isBlank() && keyword2.isBlank() && keyword3.isBlank()) {
                    isNoInput = true
                } else {
                    onePickViewModel.getRecommendedMovie(keyword1, keyword2, keyword3)

                }
            }
        ) {
            Text(
                "Search",
                fontSize = 16.sp
            )
        }

    }
}

@Composable
fun OutlinedTextFieldWithCounter(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes labelResId: Int,
    imeAction: ImeAction,
    isError: Boolean
) {
    val maxChar = 10

    OutlinedTextField(
        value = value,
        singleLine = true,
        shape = shapes.large,
        onValueChange = { if (it.length <= maxChar) { onValueChange(it) } },
        label = { Text(stringResource(labelResId)) },
        isError = isError,
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = imeAction
        ),
        supportingText = {
            Text(
                text = "${value.length} / $maxChar",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
            )
        },
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth()
    )
}

/**
 * The home screen displaying the loading message.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "キーワードにマッチする映画を探しています\uD83D\uDC40",
                style = typography.titleMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }
    }
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
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = "${content.title!!}",
                    fontWeight = FontWeight.Bold,
                    style = typography.titleLarge
                )
                Text(
                    text = "Original title: ${content.originalTitle!!}",
                    color = Color.Gray,
                    style = typography.titleSmall
                )
            }
            item {
                Row(
                    modifier = Modifier.padding(16.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://image.tmdb.org/t/p/w400/${content.posterPath}")
                            .crossfade(true)
                            .build(),
                        // placeholder = painterResource(R.drawable.placeholder),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                    Column(
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        Text(
                            text="公開年",
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 8.dp),
                            style = typography.bodyLarge,
                        )
                        Text(
                            text = " ${content.releaseDate!!.substring(0, 4)}年",
                            style = typography.bodyLarge,
                            modifier = Modifier
                                .border(1.dp, Color(0xFFEEEEEE), RectangleShape)
                                .background(Color(0xFFEEEEEE)).fillMaxWidth()
                        )
                        Text("ジャンル",
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                            style = typography.bodyLarge,
                        )
                        Text(
                            text = DisplayGenre(content.genreIds),
                            style = typography.bodyLarge,
                            modifier = Modifier
                                .border(1.dp, Color(0xFFEEEEEE), RectangleShape)
                                .background(Color(0xFFEEEEEE)).fillMaxWidth()
                        )
                        Text("スコア (TMDB)",
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                            style = typography.bodyLarge,
                        )
                        Text(
                            text = " ⭐ ${ BigDecimal(content.voteAverage!!).setScale(1, RoundingMode.HALF_UP).toDouble()} / 10",
                            style = typography.bodyLarge,
                            modifier = Modifier
                                .border(1.dp, Color(0xFFEEEEEE), RectangleShape)
                                .background(Color(0xFFEEEEEE)).fillMaxWidth()
                        )
                    }
                }
            }
            item{
                Text(text = "${content.overview!!}")
                Spacer(modifier = Modifier.height(16.dp))
                //Text(text = "${content.overview!!}")
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
}

@Composable
fun DisplayGenre(ids: List<Int>?) : String {
    val textList = ids?.mapNotNull { id ->
        val genre = genres.find { it.id == id }
        genre?.name
    }

    // テキストのリストをスペースで結合して文字列にする
    return textList?.joinToString("／") ?: ""
}

@Preview(showBackground = true)
@Composable
fun OnePickAppPreview() {
    // val onePickViewModel: OnePickViewModel = viewModel(factory = OnePickViewModel.Factory)
    OnePickTheme {
       // InitialScreen( onePickViewModel = onePickViewModel )
    }
}