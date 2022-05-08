package com.fatahapps.data.repository

import com.fatahapps.domain.entities.Resource
import com.fatahapps.domain.entities.survey.Question
import com.fatahapps.domain.repository.PulaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class FakeRepository: PulaRepository {
    private val questions = mutableListOf<Question>()

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override fun getQuestions(): Flow<Resource<List<Question>>> = flow {

        if (shouldReturnNetworkError){
            flow{ emit(Resource.Error("Error", null)) }
        } else {
            flow { emit(Resource.Success(questions)) }
        }
    }

}