package com.fatahapps.presentation.viewmodel.questions

import com.fatahapps.domain.entities.Resource
import com.fatahapps.domain.entities.survey.QuestionEntity
import com.fatahapps.domain.repository.PulaRepository
import com.fatahapps.domain.usecases.GetQuestionsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetQuestionsViewModelTest {

    private val repository = mockk<PulaRepository>()
    private lateinit var viewModel: GetQuestionsViewModel
    private val question = mockk<QuestionEntity>()

    @Before
    fun setUp() {
//        viewModel = GetQuestionsViewModel(GetQuestionsUseCase(repository))
    }

    @Test
    fun `test get questions returns questions`() = runBlocking {
        coEvery { repository.getQuestions() } returns
                flowOf(Resource.Success(listOf(question, question, question)))
        assert(viewModel.questionList.value.size == 3)
    }

}