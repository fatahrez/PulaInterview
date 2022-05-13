package com.fatahapps.presentation.viewmodel.questions

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatahapps.domain.entities.Resource
import com.fatahapps.domain.usecases.GetQuestionsUseCase
import com.fatahapps.presentation.mapper.toPresentation
import com.fatahapps.presentation.model.survey.Question
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetQuestionsViewModel @Inject constructor(
    private val getQuestionsUseCase: GetQuestionsUseCase
): ViewModel(){

    private val _state = mutableStateOf(GetQuestionsState())
    val state: State<GetQuestionsState> = _state

    private val _progressIndicator = mutableStateOf(value = 0.0f)
    val progressIndicator: State<Float> = _progressIndicator

    private val _questionIsSelected = mutableStateOf(false)
    val questionIsSelected: State<Boolean> = _questionIsSelected

    private val _questionList = mutableStateOf<List<Question>>(emptyList())
    val questionList: State<List<Question>> = _questionList

    private val _questionCount = mutableStateOf(value = 0)
    val questionCount: State<Int> = _questionCount

    private fun setQuestionIsSelected(value: Boolean) {
        _questionIsSelected.value = value
    }

    private val _title = mutableStateOf("")
    val title: State<String> = _title

    private val _buttonText = mutableStateOf("Next")
    val buttonText: State<String> = _buttonText

    private val _isNext = mutableStateOf(false)
    val isNext: State<Boolean> = _isNext

    var currentQuestion = 0

    init {
        viewModelScope.launch {
            onEvent(QuestionEvent.GetQuestions)
        }
    }

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(questionEvent: QuestionEvent) {
        when(questionEvent) {
            is QuestionEvent.GetQuestions -> {
                viewModelScope.launch {
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                    getQuestionsUseCase()
                        .onEach { result ->
                            when(result) {
                                is Resource.Success -> {
                                    _state.value = state.value.copy(
                                        questions = result.data?.map {
                                            it.toPresentation()
                                        } ?: emptyList(),
                                        isLoading = false
                                    )
                                    _questionList.value = result.data?.map {
                                        it.toPresentation()
                                    } ?: emptyList()
                                    _questionCount.value = questionList.value.size
                                    onEvent(QuestionEvent.ProgressIndicator)
                                }
                                is Resource.Error -> {
                                    _state.value = state.value.copy(
                                        questions = result.data?.map {
                                            it.toPresentation()
                                        } ?: emptyList(),
                                        isLoading = false
                                    )
                                    _eventFlow.emit(
                                        UIEvent.ShowSnackbar(
                                            result.message ?: "Unknown Error"
                                        )
                                    )
                                }
                                is Resource.Loading -> {
                                    _state.value = state.value.copy(
                                        isLoading = true
                                    )
                                }
                            }
                        }
                        .launchIn(viewModelScope)
                }
            }
            is QuestionEvent.ProgressIndicator -> {
                val progress: Float = 1/questionCount.value.toFloat()
                _progressIndicator.value = progressIndicator.value.plus(
                    progress
                )
                if (currentQuestion == questionList.value.size - 1) {
                    _buttonText.value = "Submit"
                }
            }
            is QuestionEvent.NextQuestion -> {
                viewModelScope.launch {
                    _questionIsSelected.value = false
                    _isNext.value = true
                    delay(200)
                    _isNext.value = false
                }
            }
            is QuestionEvent.NavigateToAfterQuestion -> {
                viewModelScope.launch {

                }
            }
        }
    }

    fun questionNotSelected() {
        viewModelScope.launch {
            _eventFlow.emit(
                UIEvent.ShowSnackbar("One question must be selected")
            )
        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String): UIEvent()
    }
}