package com.fatahapps.presentation.model.survey

data class Question(
    val id: String,
    val questionType: String,
    val answerType: String,
    val questionText: String,
    val options: List<Option>,
    val next: String?
)