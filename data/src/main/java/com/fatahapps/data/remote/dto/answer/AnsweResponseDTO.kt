package com.fatahapps.data.remote.dto.answer

import com.google.gson.annotations.SerializedName

data class AnsweResponseDTO(
    @SerializedName("message") val message: String
)