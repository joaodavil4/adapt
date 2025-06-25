package com.example.adapt.domain.usecase.color


import android.graphics.Bitmap
import android.util.Log
import com.example.adapt.BuildConfig
import com.example.adapt.ui.Result
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SendPromptUseCase {

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.apiKey
    )

    fun execute(bitmap: Bitmap? = null, prompt: String): Flow<Result<String>> = flow {
        emit(Result.Loading) // Emit loading state
        try {
            val response = generativeModel.generateContent(
                content {
                    bitmap?.let { image(it) }
                    text(prompt)
                }
            )

            response.text?.let { outputContent ->
                Log.d("SendPromptUseCase", "Response: $response")
                emit(Result.Success(outputContent)) // Emit success state
            } ?: emit(Result.Error("Empty response")) // Emit error state for empty response
        } catch (e: Exception) {
            emit(
                Result.Error(
                    e.localizedMessage ?: "Unknown error",
                    e
                )
            ) // Emit error state for exceptions
        }
    }
}