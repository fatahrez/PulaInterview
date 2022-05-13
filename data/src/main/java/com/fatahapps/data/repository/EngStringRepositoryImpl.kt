package com.fatahapps.data.repository

import com.fatahapps.data.local.EngStringsDao
import com.fatahapps.data.mapper.toDomain
import com.fatahapps.data.mapper.toLocal
import com.fatahapps.data.remote.PulaApi
import com.fatahapps.domain.entities.Resource
import com.fatahapps.domain.entities.survey.EngStringsEntity
import com.fatahapps.domain.repository.EngStringRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class EngStringRepositoryImpl @Inject constructor(
    private val api: PulaApi,
    private val dao: EngStringsDao
): EngStringRepository {
    override fun getStrings(): Flow<Resource<EngStringsEntity>> = flow {
        emit(Resource.Loading())

        val engStringsDao = dao.getEngStrings()

        try {
            val remoteData = api.getQuestions().strings
            dao.deleteEngStrings()
            dao.insertEngStrings(remoteData.en.toLocal())
        } catch (e: HttpException) {
            emit(Resource.Error(
                message = e.message(),
                data = engStringsDao.toDomain()
            ))
        } catch (e: IOException) {
            emit(Resource.Error(
                message = e.message.toString(),
                data = engStringsDao.toDomain()
            ))
        }

        val finalEngStringDao = dao.getEngStrings().toDomain()
        emit(Resource.Success(finalEngStringDao))
    }
}