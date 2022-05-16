package com.fatahapps.data.remote.dto.answer

import com.google.gson.annotations.SerializedName

data class AnswerDTO (
    @SerializedName("photo") val bitmap: String?,
    @SerializedName("answer_list") val answerList: List<String>
)