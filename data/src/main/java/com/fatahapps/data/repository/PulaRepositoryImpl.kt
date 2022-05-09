package com.fatahapps.data.repository

import com.fatahapps.data.mapper.toDomain
import com.fatahapps.data.remote.PulaApi
import com.fatahapps.domain.entities.Resource
import com.fatahapps.domain.entities.survey.QuestionEntity
import com.fatahapps.domain.repository.PulaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PulaRepositoryImpl @Inject constructor(
    private val api: PulaApi
): PulaRepository {
    override fun getQuestions(): Flow<Resource<List<QuestionEntity>>> = flow {
        emit(Resource.Loading())
        try {
            val remoteData = api.getQuestions()
            emit(Resource.Success(remoteData
                .questions.map {
                it.toDomain()
                }
            ))
        } catch (e: HttpException) {
            emit(Resource.Error(
                message = e.message(),
                data = null
            ))
        } catch (e: IOException) {
            emit(Resource.Error(
                message = e.message.toString(),
                data = null
            ))
        }
    }
}