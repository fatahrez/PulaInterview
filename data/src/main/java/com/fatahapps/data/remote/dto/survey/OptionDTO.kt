package com.fatahapps.data.remote.dto.survey

import com.google.gson.annotations.SerializedName

data class OptionDTO(
    @SerializedName("value")
    val value: String,
    @SerializedName("display_text")
    val displayText: String
)