package com.example.data.remote

import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

@Serializable
data class GenerateContentRequest(
    val contents: List<Content>,
    @SerialName("system_instruction") val systemInstruction: Content? = null,
    val generationConfig: GenerationConfig? = null
)

@Serializable
data class Content(
    val role: String? = null,
    val parts: List<Part>
)

@Serializable
data class Part(
    val text: String? = null
)

@Serializable
data class GenerationConfig(
    val temperature: Float? = null,
    val topP: Float? = null,
    val topK: Int? = null
)

@Serializable
data class GenerateContentResponse(
    val candidates: List<Candidate>? = null,
    val error: ApiError? = null
)

@Serializable
data class Candidate(
    val content: Content? = null
)

@Serializable
data class ApiError(
    val code: Int? = null,
    val message: String? = null,
    val status: String? = null
)

object GeminiClient {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent"

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
    }

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    suspend fun askNeetDoubt(question: String): Result<String> = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isBlank()) {
            return@withContext Result.failure(
                Exception("Gemini API Key is not set in environment. Please add GEMINI_API_KEY to AI Studio secrets.")
            )
        }

        val systemPrompt = """
            You are Mission Lakshya NEET 2027 AI Tutor.
            Help students with NEET Physics, Chemistry and Biology questions and concepts.
            Explain concepts clearly in bilingual Hindi and English (simple Hinglish or clear Hindi).
            For numerical questions:
            1. Given Data
            2. Formula used
            3. Step-by-step calculation
            4. Final Answer with correct SI units.
            Highlight high-yield NEET tips and NCERT citations where relevant.
        """.trimIndent()

        val reqObj = GenerateContentRequest(
            systemInstruction = Content(
                parts = listOf(Part(text = systemPrompt))
            ),
            contents = listOf(
                Content(
                    role = "user",
                    parts = listOf(Part(text = question))
                )
            ),
            generationConfig = GenerationConfig(
                temperature = 0.7f,
                topP = 0.95f
            )
        )

        try {
            val jsonBody = json.encodeToString(reqObj)
            val request = Request.Builder()
                .url("$BASE_URL?key=$apiKey")
                .post(jsonBody.toRequestBody("application/json; charset=utf-8".toMediaType()))
                .build()

            val response = okHttpClient.newCall(request).execute()
            val responseBody = response.body?.string() ?: ""

            if (response.isSuccessful && responseBody.isNotBlank()) {
                val parsed = json.decodeFromString<GenerateContentResponse>(responseBody)
                val text = parsed.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                if (!text.isNullOrBlank()) {
                    Result.success(text)
                } else {
                    Result.failure(Exception("AI couldn't generate a solution."))
                }
            } else {
                val errorMsg = try {
                    val errorParsed = json.decodeFromString<GenerateContentResponse>(responseBody)
                    errorParsed.error?.message ?: "Server error: ${response.code}"
                } catch (e: Exception) {
                    "Error (${response.code}): $responseBody"
                }
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
