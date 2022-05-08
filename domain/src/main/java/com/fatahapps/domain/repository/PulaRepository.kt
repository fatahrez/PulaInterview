package com.fatahapps.domain.repository

import com.fatahapps.domain.entities.Resource
import com.fatahapps.domain.entities.survey.Question
import kotlinx.coroutines.flow.Flow

interface PulaRepository {
    fun getQuestions(): Flow<Resource<List<Question>>>
}