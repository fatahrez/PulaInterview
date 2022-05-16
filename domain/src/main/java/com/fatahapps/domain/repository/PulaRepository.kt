package com.fatahapps.domain.repository

import com.fatahapps.domain.entities.Resource
import com.fatahapps.domain.entities.answer.AnswerEntity
import com.fatahapps.domain.entities.survey.EngStringsEntity
import com.fatahapps.domain.entities.survey.QuestionEntity
import kotlinx.coroutines.flow.Flow

interface PulaRepository {
    fun getQuestions(): Flow<Resource<List<QuestionEntity>>>

    fun postAnswers(answerEntity: AnswerEntity): Flow<Resource<String>>
}