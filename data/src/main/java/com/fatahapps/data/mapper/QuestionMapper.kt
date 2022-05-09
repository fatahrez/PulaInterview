package com.fatahapps.data.mapper

import com.fatahapps.data.local.model.OptionLocal
import com.fatahapps.data.local.model.QuestionLocal
import com.fatahapps.data.remote.dto.survey.OptionDTO
import com.fatahapps.data.remote.dto.survey.QuestionDTO
import com.fatahapps.domain.entities.survey.OptionEntity
import com.fatahapps.domain.entities.survey.QuestionEntity

fun QuestionDTO.toDomain(): QuestionEntity =
    QuestionEntity(
        id,
        questionType,
        answerType,
        questionText,
        optionDTOs.map {
           it.toDomain()
        },
        next
    )

fun QuestionDTO.toLocal(): QuestionLocal =
    QuestionLocal(
        id,
        questionType,
        answerType,
        questionText,
        optionDTOs.map {
           it.toLocal()
        },
        next
    )

fun QuestionLocal.toDomain(): QuestionEntity =
    QuestionEntity(
        id,
        questionType,
        answerType,
        questionText,
        optionLocals.map {
            it.toDomain()
        },
        next
    )

fun OptionDTO.toDomain(): OptionEntity =
    OptionEntity(
        value,
        displayText
)

fun OptionDTO.toLocal(): OptionLocal =
    OptionLocal(
        value,
        displayText
    )

fun OptionLocal.toDomain(): OptionEntity =
    OptionEntity(
        value,
        displayText
    )