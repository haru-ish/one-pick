package com.example.onepick.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class Choice(
    @SerialName("index") val index: Int,
    @SerialName("message") val message: Message,
    @SerialName("finish_reason") val finishReason: String
)
