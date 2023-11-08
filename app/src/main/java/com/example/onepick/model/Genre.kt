package com.example.onepick.model

/**
 * 　映画のジャンルを表すデータクラス
 */
data class Genre(val id: Int, val name: String)

val genres = listOf(
    Genre(28, "アクション"),
    Genre(12, "冒険"),
    Genre(16, "アニメ"),
    Genre(35, "コメディ"),
    Genre(80, "犯罪"),
    Genre(99, "ドキュメンタリー"),
    Genre(18, "ドラマ"),
    Genre(10751, "ファミリー"),
    Genre(14, "ファンタジー"),
    Genre(36, "歴史"),
    Genre(27, "ホラー"),
    Genre(10402, "音楽"),
    Genre(9648, "ミステリー"),
    Genre(10749, "恋愛"),
    Genre(878, "SF"),
    Genre(10770, "テレビ映画"),
    Genre(53, "スリラー"),
    Genre(10752, "戦争"),
    Genre(37, "西部劇"),
)