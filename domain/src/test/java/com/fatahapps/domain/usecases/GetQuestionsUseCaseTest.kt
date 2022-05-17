package com.fatahapps.domain.usecases

import com.fatahapps.domain.entities.Resource
import com.fatahapps.domain.entities.answer.AnswerEntity
import com.fatahapps.domain.entities.survey.EngStringsEntity
import com.fatahapps.domain.entities.survey.QuestionEntity
import com.fatahapps.domain.repository.PulaRepository
import io.mockk.mockk
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetQuestionsUseCaseTest {

    companion object {
        fun mockRepository(
            flowReturn: Flow<Resource<List<QuestionEntity>>>,
            flowStringReturn: Flow<Resource<String>>
        ) = object : PulaRepository {
            override fun getQuestions(): Flow<Resource<List<QuestionEntity>>> = flowReturn
            override fun postAnswers(answerEntity: AnswerEntity): Flow<Resource<String>> =
                flowStringReturn
        }
    }

    @Test
    fun `Get questions starts with loading RETURNS Resource Loading`() = runBlocking {
        val question = mockk<QuestionEntity>()

        val repository = mockRepository(flow {
            emit(Resource.Loading())
            emit(Resource.Success(listOf(question, question, question)))
        },flow {
            emit(Resource.Success(""))
        })

        val result = GetQuestionsUseCase(repository).invoke().first()

        assert((result is Resource.Loading))
    }

    @Test
    fun `get questions empty result RETURNS emptyList`() = runBlocking {
        val repository = mockRepository(flowOf(Resource.Success(emptyList())),
            flow {
                emit(Resource.Success(""))
            })

        val result = GetQuestionsUseCase(repository).invoke().last()

        assert(result.data?.isEmpty() ?: false)
    }

    @Test
    fun `get questions success result RETURNS Resource + Data`() = runBlocking {
        val question = mockk<QuestionEntity>()

        val repository = mockRepository(flow {
            emit(Resource.Loading())
            emit(Resource.Success(listOf(question, question, question)))
        }, flow {
            emit(Resource.Success(""))
        })

        val result = GetQuestionsUseCase(repository).invoke().last()

        assert(result is Resource.Success && (result.data?.size ?: false) == 3)
    }

    @Test
    fun `get questions error RETURNS Resource Error`() = runBlocking {
        val repository = mockRepository(flow {
            emit(Resource.Error("error getting questions"))
        },
        flow {
            emit(Resource.Success(""))
        })

        val result = GetQuestionsUseCase(repository).invoke().last()

        assert(result is Resource.Error && result.message == "error getting questions")
    }
}