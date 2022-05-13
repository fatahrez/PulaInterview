package com.fatahapps.presentation.viewmodel.engstrings

import com.fatahapps.presentation.model.survey.EngStrings

data class EngStringsState(
    val isLoading: Boolean = false,
    val engStrings: EngStrings? = null
)
