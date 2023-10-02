package com.example.onepick.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class ChatGptRequest(
    @SerialName("model") val model: String,
    @SerialName("messages") val messages: List<Message>
)
