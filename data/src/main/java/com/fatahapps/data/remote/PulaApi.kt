package com.fatahapps.data.remote

import com.fatahapps.data.remote.dto.answer.AnsweResponseDTO
import com.fatahapps.data.remote.dto.answer.AnswerDTO
import com.fatahapps.data.remote.dto.survey.SurveyWrapperDTO
import retrofit2.Response
import retrofit2.http.*

interface PulaApi {
    companion object {
        const val BASE_URL = "https://run.mocky.io/v3/"
    }

    @GET("d628facc-ec18-431d-a8fc-9c096e00709a")
    suspend fun getQuestions(): SurveyWrapperDTO

    @POST("d628facc-ec18-431d-a8fc-9c096e00709a")
    suspend fun postAnswer(
        @Body answerDTO: AnswerDTO
    ): AnsweResponseDTO
}