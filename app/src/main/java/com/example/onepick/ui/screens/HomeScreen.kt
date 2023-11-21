@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.onepick.ui.screens

import androidx.annotation.StringRes
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.onepick.R
import com.example.onepick.model.Movie
import com.example.onepick.model.genres
import com.example.onepick.ui.OnePickUiState
import java.math.BigDecimal
import java.math.RoundingMode


@Composable
fun HomeScreen(
    onePickViewModel: OnePickViewModel,
    modifier: Modifier = Modifier
){
    // ViewModelからUIStateを受け取る
    val uiState = onePickViewModel.onePickUiState
    // UIStateによって、表示する画面を管理
    when (uiState) {
        is OnePickUiState.Initial -> InitialScreen(onePickViewModel, modifier = modifier.fillMaxSize())
        is OnePickUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is OnePickUiState.Success -> ResultScreen(
            (uiState as OnePickUiState.Success).content, onePickViewModel, modifier = modifier.fillMaxWidth()
        )
        is OnePickUiState.Error -> ErrorScreen( (uiState as OnePickUiState.Error).msg, onePickViewModel, modifier = modifier.fillMaxSize())
    }
}

/**
 * ユーザーがキーワードを入力し、検索ボタンを押下する画面
 */
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
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.app_description),
            style = typography.titleSmall
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
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
                // 入力チェック(全てのキーワードが空文字だった場合、エラーを返す)
                if(keyword1.isBlank() && keyword2.isBlank() && keyword3.isBlank()) {
                    isNoInput = true
                } else {
                    // chatGptApiと通信
                    //onePickViewModel.getMovieTitle(keyword1, keyword2, keyword3)
                    onePickViewModel.getRecommendedMovie(keyword1, keyword2, keyword3)
                }
            }
        ) {
            Text(
                stringResource(R.string.search),
                fontSize = 16.sp
            )
        }

    }
}

/**
 * ユーザーがキーワードを入力する際のフォーマット
 */
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
        // 10文字以上入力できないように制限
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
            .padding(bottom = dimensionResource(id = R.dimen.padding_medium))
            .fillMaxWidth()
    )
}

/**
 * ローディングメッセージを表示する画面
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
                text = stringResource(R.string.loading_description),
                style = typography.titleMedium
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
            // ローディング中のアイコン(ぐるぐる回る)を表示
            CircularProgressIndicator()
        }
    }
}

/**
 * エラーメッセージと再試行ボタンを表示する画面
 */
@Composable
fun ErrorScreen(
    msg: String,
    onePickViewModel: OnePickViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            stringResource(R.string.error),
            tint = Color.Gray,
            modifier = Modifier.size(96.dp)
        )
        Text(text = "$msg",
            style = typography.titleMedium,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        )
        Button(
            onClick = {
                // UIStateをInitialにリセット
                onePickViewModel.resetAppState()
            }
        ) {
            Text(stringResource(R.string.retry))
        }
    }
}

/**
 * 検索結果(映画の詳細)を表示する画面
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
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    CloseButton {
                        // UIStateをInitialにリセット
                        onePickViewModel.resetAppState()
                    }
                }
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
                Text(
                    text = "${content.title!!}",
                    fontWeight = FontWeight.Bold,
                    style = typography.titleLarge,
                    modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_small), end = dimensionResource(id = R.dimen.padding_small))
                )
                Text(
                    text = "Original title: ${content.originalTitle!!}",
                    color = Color.Gray,
                    style = typography.titleSmall,
                    modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_small), end = dimensionResource(id = R.dimen.padding_small))
                )
            }
            item {
                Row(
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://image.tmdb.org/t/p/w400/${content.posterPath}")
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                    Column(
                        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
                    ) {
                        InfoText(stringResource(R.string.result_label_1),
                            content.releaseDate!!.substring(0, 4) + "年"
                        )
                        InfoText(
                            stringResource(R.string.result_label_2),
                            DisplayGenre(content.genreIds),
                            Modifier.padding(top = dimensionResource(id = R.dimen.padding_small))
                        )
                        InfoText(
                            stringResource(R.string.result_label_3),
                            "⭐ ${BigDecimal(content.voteAverage!!).setScale(1,
                                RoundingMode.HALF_UP).toDouble()} / 10", Modifier.padding(top = dimensionResource(id = R.dimen.padding_small))
                        )
                    }
                }
            }
            item{
                Column(
                    modifier = Modifier.padding(
                        start = dimensionResource(id = R.dimen.padding_small),
                        end = dimensionResource(id = R.dimen.padding_small),
                        bottom = dimensionResource(id = R.dimen.padding_extraLarge)
                    ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "${content.overview!!}")
                }
            }
        }
    }
}

/**
 * 映画の詳細(公開年、ジャンル、スコア)を表示するフォーマット
 */
@Composable
fun InfoText(
    label: String,
    value: String,
    labelModifier: Modifier = Modifier,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = label,
            color = Color.Gray,
            modifier = labelModifier.padding(bottom = dimensionResource(id = R.dimen.padding_small)),
            style = typography.bodyLarge,
        )
        Text(
            text = value,
            style = typography.bodyLarge,
            modifier = Modifier
                .border(1.dp, Color(0xFFEEEEEE), RectangleShape)
                .background(Color(0xFFEEEEEE))
                .fillMaxWidth()
        )
    }
}

/**
 * Intで返ってきたジャンルのリストを文字列に変換して表示するファンクション
 */
@Composable
fun DisplayGenre(ids: List<Int>?) : String {
    val textList = ids?.map { id ->
        val genre = genres.find { it.id == id }
        genre?.name
    }
    // テキストのリストをスペースで結合して文字列にする
    return textList?.joinToString("／") ?: ""
}

/**
 * 検索結果を閉じる際のボタン
 */
@Composable
fun CloseButton(
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            stringResource(id = R.string.close),
            tint = Color.Gray,
            modifier = Modifier.size(dimensionResource(id = R.dimen.padding_extraLarge))
        )
    }
}