package com.fatahapps.presentation.mapper

import com.fatahapps.domain.entities.survey.OptionEntity
import com.fatahapps.domain.entities.survey.QuestionEntity
import com.fatahapps.presentation.model.survey.Option
import com.fatahapps.presentation.model.survey.Question

fun QuestionEntity.toPresentation(): Question =
    Question(
        id,
        questionType,
        answerType,
        questionText,
        optionEntities.map {
            it.toPresentation()
        },
        next
    )

fun OptionEntity.toPresentation(): Option =
    Option(
        value,
        displayText
    )