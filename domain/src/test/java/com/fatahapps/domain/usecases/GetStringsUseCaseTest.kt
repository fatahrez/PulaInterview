package com.fatahapps.domain.usecases

import com.fatahapps.domain.entities.Resource
import com.fatahapps.domain.entities.survey.EngStringsEntity
import com.fatahapps.domain.repository.EngStringRepository
import io.mockk.mockk
import org.junit.Assert.*

import junit.framework.TestCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetStringsUseCaseTest {
    companion object {
        fun mockStrRepository(
            flowReturnString: Flow<Resource<EngStringsEntity>>
        ) = object : EngStringRepository {
                override fun getStrings(): Flow<Resource<EngStringsEntity>> = flowReturnString
        }
    }

    @Test
    fun `Get strings starts with loading RETURNS Resource Loading`() = runBlocking {
        val stringsObject = mockk<EngStringsEntity>()

        val repository = mockStrRepository(flow {
            emit(Resource.Loading())
            emit(Resource.Success(stringsObject))
        })

        val result = GetStringsUseCase(repository).invoke().first()

        assert(result is Resource.Loading)
    }

    @Test
    fun `get strings success result RETURNS Resource + data`() = runBlocking {
        val stringsObject = mockk<EngStringsEntity>()

        val repository = mockStrRepository(flow {
            emit(Resource.Loading())
            emit(Resource.Success(stringsObject))
        })

        val result = GetStringsUseCase(repository).invoke().last()

        assert(result is Resource.Success && (result.data != null))
    }

    @Test
    fun `get strings error RETURNS Resource Error`() = runBlocking {
        val repository = mockStrRepository(flow {
            emit(Resource.Error("error getting en string"))
        })

        val result = GetStringsUseCase(repository).invoke().last()

        assert(result is Resource.Error && result.message == "error getting en string")
    }
}