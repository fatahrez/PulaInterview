package com.fatahapps.domain.reppository

import com.fatahapps.domain.entities.Resource
import com.fatahapps.domain.entities.survey.QuestionEntity
import com.fatahapps.domain.repository.PulaRepository
import com.fatahapps.presentation.model.survey.Question
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRepository: PulaRepository {
    val question = QuestionEntity("id", "STRING",
    "STRING", "Question", listOf(), null)

    private val questions = listOf<QuestionEntity>(question, question)

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override fun getQuestions(): Flow<Resource<List<QuestionEntity>>> = flow {

        if (shouldReturnNetworkError){
            flow{ emit(Resource.Error("Error", null)) }
        } else {
            flow { emit(Resource.Success(questions)) }
        }
    }
}