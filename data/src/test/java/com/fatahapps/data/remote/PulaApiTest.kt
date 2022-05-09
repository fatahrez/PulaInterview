package com.fatahapps.data.remote

import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStreamReader

class PulaApiTest {

    companion object {
        fun jsonToString(path: String): String {
            val resourceStream = javaClass.classLoader?.getResourceAsStream(path)
            val reader = InputStreamReader(resourceStream)
            return reader.use { it.readText() }
        }
    }

    @get:Rule
    val mockServer = MockWebServer()

    private val api by lazy {
        Retrofit.Builder()
            .baseUrl(mockServer.url("/"))
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PulaApi::class.java)
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }

    @Test
    fun `get questions success RETURNS number of questions`() = runBlocking {
        val response = MockResponse()
            .setBody(jsonToString("survey_questions.json"))
            .setResponseCode(200)

        mockServer.enqueue(response =response)

        val questions = api.getQuestions()

        assert(questions.questions.size == 3)
    }

    @Test
    fun `get questions success RETURNS false wrong number of questions`() = runBlocking{
        val response = MockResponse()
            .setBody(jsonToString("survey_questions.json"))
            .setResponseCode(200)

        mockServer.enqueue(response =response)

        val questions = api.getQuestions()

        assert(questions.questions.size != 4)
    }
}