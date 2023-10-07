package com.example.onepick.data

import com.example.onepick.model.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TmdbResponse(
//    @SerialName("overview") val overview: String,
//    @SerialName("release_date") val releaseDate: String,
//    @SerialName("title") val title: String,
//    @SerialName("vote_average") val voteAverage: String,
    @SerialName("page") val page: Int,
    @SerialName("results") val results: List<Movie>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int

)

