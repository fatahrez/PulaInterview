package com.fatahapps.presentation.viewmodel.questions

import com.fatahapps.presentation.model.survey.Question

data class GetQuestionsState(
    val questions: List<Question> = emptyList(),
    val isLoading: Boolean = false
)
