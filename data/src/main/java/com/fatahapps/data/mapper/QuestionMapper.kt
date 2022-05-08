package com.fatahapps.data.mapper

import com.fatahapps.data.remote.dto.survey.QuestionDTO
import com.fatahapps.domain.entities.survey.Question

fun QuestionDTO.toDomain(): Question =
    Question(
        id,
        questionType,
        answerType,
        questionText,
        options,
        next
    )