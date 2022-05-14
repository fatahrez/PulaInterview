package com.fatahapps.domain.usecases

import com.fatahapps.domain.entities.Resource
import com.fatahapps.domain.repository.PulaRepository
import org.intellij.lang.annotations.Flow
import javax.inject.Inject

class PostAnswersUseCase @Inject constructor(
    private val repository: PulaRepository
) {
    operator fun invoke(repository: PulaRepository){

    }
}