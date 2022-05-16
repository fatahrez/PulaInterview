package com.fatahapps.domain.usecases

import com.fatahapps.domain.entities.Resource
import com.fatahapps.domain.entities.answer.AnswerEntity
import com.fatahapps.domain.repository.PulaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostAnswersUseCase @Inject constructor(
    private val repository: PulaRepository
) {
    operator fun invoke(answerEntity: AnswerEntity): Flow<Resource<String>>{
        return repository.postAnswers(answerEntity)
    }
}