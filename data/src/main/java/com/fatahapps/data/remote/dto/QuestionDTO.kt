package com.fatahapps.data.remote.dto

import com.fatahapps.domain.entities.survey.Option
import com.google.gson.annotations.SerializedName

data class QuestionDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("question_type")
    val questionType: String,
    @SerializedName("answer_type")
    val answerType: String,
    @SerializedName("question_text")
    val questionText: String,
    @SerializedName("options")
    val options: List<Option>,
    @SerializedName("next")
    val next: String?
)
