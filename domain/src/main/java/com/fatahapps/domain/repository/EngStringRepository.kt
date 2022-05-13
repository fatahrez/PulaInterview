package com.fatahapps.domain.repository

import com.fatahapps.domain.entities.Resource
import com.fatahapps.domain.entities.survey.EngStringsEntity
import kotlinx.coroutines.flow.Flow

interface EngStringRepository {
    fun getStrings(): Flow<Resource<EngStringsEntity>>
}