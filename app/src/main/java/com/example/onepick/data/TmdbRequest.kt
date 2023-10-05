package com.example.onepick.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TmdbRequest(
    // ダミー
    @SerialName("id") val id: String
)
