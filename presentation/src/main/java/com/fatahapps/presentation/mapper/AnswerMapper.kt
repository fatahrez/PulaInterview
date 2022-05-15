package com.fatahapps.presentation.mapper

import com.fatahapps.domain.entities.answer.AnswerEntity
import com.fatahapps.presentation.model.answer.Answer

fun Answer.toDomain(): AnswerEntity =
    AnswerEntity(
        bitmap,
        answerList
    )