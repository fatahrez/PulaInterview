package com.fatahapps.data.remote.dto.survey

import com.fatahapps.domain.entities.survey.OptionEntity
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
    val optionEntities: List<OptionEntity>,
    @SerializedName("next")
    val next: String?
)
