package com.example.onepick.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class ChatGptResponse(
    @SerialName("id") val id: String,
    @SerialName("object") val `object`: String,
    @SerialName("created") val created: Long,
    @SerialName("model") val model: String,
    @SerialName("choices") val choices: List<Choice>,
    @SerialName("usage") val usage: Usage
)
