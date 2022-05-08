package com.fatahapps.domain.entities.survey

data class QuestionEntity(
    val id: String,
    val questionType: String,
    val answerType: String,
    val questionText: String,
    val optionEntities: List<OptionEntity>,
    val next: String?
)
