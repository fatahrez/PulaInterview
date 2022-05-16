package com.fatahapps.data.mapper

import com.fatahapps.data.local.model.answer.AnswerLocal
import com.fatahapps.data.remote.dto.answer.AnswerDTO
import com.fatahapps.domain.entities.answer.AnswerEntity

fun AnswerEntity.toDto(): AnswerDTO =
    AnswerDTO(
        bitmap,
        answerList
    )

fun AnswerEntity.toLocal(): AnswerLocal =
    AnswerLocal(
        id = null,
        bitmap,
        answerList
    )

fun AnswerLocal.toDomain(): AnswerEntity =
    AnswerEntity(
        bitmap,
        answerList
    )