package com.fatahapps.domain.usecases

import com.fatahapps.domain.entities.Resource
import com.fatahapps.domain.entities.survey.EngStringsEntity
import com.fatahapps.domain.repository.EngStringRepository
import com.fatahapps.domain.repository.PulaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStringsUseCase @Inject constructor(
    private val repository: EngStringRepository
) {
    operator fun invoke(): Flow<Resource<EngStringsEntity>> {
        return repository.getStrings()
    }
}