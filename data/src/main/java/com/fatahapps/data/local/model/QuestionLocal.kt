package com.fatahapps.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fatahapps.domain.entities.survey.OptionEntity

@Entity
data class QuestionLocal (
    @PrimaryKey val id: String,
    val questionType: String,
    val answerType: String,
    val questionText: String,
    val optionEntities: List<OptionLocal>,
    val next: String?
)