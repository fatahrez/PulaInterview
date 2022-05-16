package com.fatahapps.data.repository

import android.util.Log
import com.fatahapps.data.local.AnswerDao
import com.fatahapps.data.local.QuestionDao
import com.fatahapps.data.mapper.toDomain
import com.fatahapps.data.mapper.toDto
import com.fatahapps.data.mapper.toLocal
import com.fatahapps.data.remote.PulaApi
import com.fatahapps.domain.entities.Resource
import com.fatahapps.domain.entities.answer.AnswerEntity
import com.fatahapps.domain.entities.survey.QuestionEntity
import com.fatahapps.domain.repository.PulaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Retrofit
import java.io.IOException
import javax.inject.Inject

class PulaRepositoryImpl @Inject constructor(
    private val api: PulaApi,
    private val dao: QuestionDao,
    private val answerDao: AnswerDao
): PulaRepository {
    override fun getQuestions(): Flow<Resource<List<QuestionEntity>>> = flow {
        emit(Resource.Loading())

        val questionsDao = dao.getQuestions()

        try {
            val remoteData = api.getQuestions()
            dao.insertQuestions(remoteData.questions.map {
                it.toLocal()
            })
        } catch (e: HttpException) {
            emit(Resource.Error(
                message = e.message(),
                data = questionsDao.map {
                    it.toDomain()
                }
            ))
        } catch (e: IOException) {
            emit(Resource.Error(
                message = e.message.toString(),
                data = questionsDao.map {
                    it.toDomain()
                }
            ))
        }

        // Get final data from caching - single source of truth
        val finalQuestionsDao = dao.getQuestions().map {
            it.toDomain()
        }

        emit(Resource.Success(finalQuestionsDao))
    }

    override fun postAnswers(answerEntity: AnswerEntity):Flow<Resource<String>> = flow {
        emit(Resource.Loading())

        try {
            api.postAnswer(
                answerEntity.toDto()
            )
            answerDao.deleteAnswer()
            answerDao.insertAnswer(answerEntity.toLocal())
            emit(Resource.Success("Message"))
        } catch (e: HttpException){
            emit(Resource.Error(
                message = e.message.toString()
            ))
        } catch (e: IOException) {
            emit(Resource.Error(
                message = e.message.toString()
            ))
        }
    }
}