package com.example.onepick.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TmdbResponse(
    @SerialName("overview") val overview: String,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("title") val title: String,
    @SerialName("vote_average") val voteAverage: String
)