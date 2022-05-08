package com.fatahapps.domain.usecases

import com.fatahapps.domain.entities.Resource
import com.fatahapps.domain.entities.survey.Question
import com.fatahapps.domain.repository.PulaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(
    private val repository: PulaRepository
) {
    operator fun invoke(): Flow<Resource<List<Question>>> {
        return repository.getQuestions()
    }
}