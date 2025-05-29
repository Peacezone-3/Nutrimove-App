package com.nutrimove.ai

import retrofit2.Call
import retrofit2.http.*
import com.google.gson.annotations.SerializedName

data class ChatRequest(
    @SerializedName("model") val model: String = "gpt-3.5-turbo",
    @SerializedName("messages") val messages: List<Message>
)

data class Message(
    val role: String,
    val content: String
)

data class ChatResponse(
    @SerializedName("choices") val choices: List<Choice>
)

data class Choice(
    @SerializedName("message") val message: Message
)

interface OpenAIService {
    @POST("v1/chat/completions")
    fun getMealPlan(
        @Header("Authorization") authHeader: String,
        @Body request: ChatRequest
    ): Call<ChatResponse>
}
