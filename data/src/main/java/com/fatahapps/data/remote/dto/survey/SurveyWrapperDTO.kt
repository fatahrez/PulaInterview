package com.fatahapps.data.remote.dto.survey

import com.google.gson.annotations.SerializedName

data class SurveyWrapperDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("start_question_id")
    val startQuestionId: String,
    @SerializedName("questions")
    val questions: List<QuestionDTO>,
    @SerializedName("strings")
    val strings: StringsWrapper
)

data class StringsWrapper(
    @SerializedName("en")
    val en: EngStringsDTO
)