package com.fatahapps.data.mapper

import com.fatahapps.data.remote.dto.survey.QuestionDTO
import com.fatahapps.domain.entities.survey.QuestionEntity

fun QuestionDTO.toDomain(): QuestionEntity =
    QuestionEntity(
        id,
        questionType,
        answerType,
        questionText,
        optionEntities,
        next
    )