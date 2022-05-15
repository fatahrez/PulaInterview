package com.fatahapps.data.mapper

import com.fatahapps.data.remote.dto.answer.AnswerDTO
import com.fatahapps.domain.entities.answer.AnswerEntity

fun AnswerEntity.toDto(): AnswerDTO =
    AnswerDTO(
        bitmap,
        answerList
    )