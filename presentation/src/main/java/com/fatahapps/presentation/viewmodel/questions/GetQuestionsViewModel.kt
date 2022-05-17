package com.fatahapps.presentation.viewmodel.questions

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatahapps.domain.entities.Resource
import com.fatahapps.domain.usecases.GetQuestionsUseCase
import com.fatahapps.domain.usecases.GetStringsUseCase
import com.fatahapps.domain.usecases.PostAnswersUseCase
import com.fatahapps.presentation.mapper.toDomain
import com.fatahapps.presentation.mapper.toPresentation
import com.fatahapps.presentation.model.answer.Answer
import com.fatahapps.presentation.model.survey.Question
import com.fatahapps.presentation.viewmodel.engstrings.EngStringsState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetQuestionsViewModel @Inject constructor(
    private val getQuestionsUseCase: GetQuestionsUseCase,
    private val getStringsUseCase: GetStringsUseCase,
    private val postAnswersUseCase: PostAnswersUseCase
): ViewModel(){

    private val _state = mutableStateOf(GetQuestionsState())
    val state: State<GetQuestionsState> = _state

    private val _progressIndicator = mutableStateOf(value = 0.0f)
    val progressIndicator: State<Float> = _progressIndicator

    private val _questionList = mutableStateOf<List<Question>>(emptyList())
    val questionList: State<List<Question>> = _questionList

    private val _questionCount = mutableStateOf(value = 0)
    val questionCount: State<Int> = _questionCount

    val _answer = mutableStateOf<Answer>(Answer("", listOf("")))
    private val answer: State<Answer> = _answer

    private val _stringState = mutableStateOf(EngStringsState())
    val stringState: State<EngStringsState> = _stringState

    private val _buttonText = mutableStateOf("Next")
    val buttonText: State<String> = _buttonText

    private val _isNext = mutableStateOf(false)
    val isNext: State<Boolean> = _isNext

    private val _imageUrl = mutableStateOf(value = "")
    val image: State<String> = _imageUrl

    private val _imageBitmap = mutableStateOf<Bitmap>(
        value = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    )
    val imageBitmap: State<Bitmap> =_imageBitmap


    var currentQuestion = 0

    init {
        viewModelScope.launch {
            onEvent(QuestionEvent.GetEngStrings)
        }
    }

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(questionEvent: QuestionEvent) {
        when(questionEvent) {
            is QuestionEvent.GetEngStrings -> {
                viewModelScope.launch {
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                    getStringsUseCase().onEach{result ->
                        when(result) {
                            is Resource.Success -> {
                                _stringState.value = stringState.value.copy(
                                    engStrings = result.data?.toPresentation(),
                                    isLoading = false
                                )
                                onEvent(QuestionEvent.GetQuestions)
                            }
                            is Resource.Error -> {
                                _eventFlow.emit(
                                    UIEvent.ShowSnackbar(
                                        result.message ?: "Unknown Error"
                                    )
                                )
                                _stringState.value = stringState.value.copy(
                                    isLoading = false,
                                    engStrings = result.data?.toPresentation()
                                )
                                onEvent(QuestionEvent.GetQuestions)
                            }
                            is Resource.Loading -> {
                                _stringState.value = stringState.value.copy(
                                    isLoading = true
                                )
                            }
                        }
                    }.launchIn(viewModelScope)
                }
            }
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
                                        isLoading = false
                                    )
                                    _questionList.value = result.data?.map {
                                        it.toPresentation()
                                    } ?: emptyList()
                                    _questionCount.value = questionList.value.size
                                    onEvent(QuestionEvent.ProgressIndicator)
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
                    _buttonText.value = "Finish"
                    onEvent(QuestionEvent.GetPhoto)
                }
            }
            is QuestionEvent.NextQuestion -> {
                viewModelScope.launch {
                    _isNext.value = true
                    delay(200)
                    _isNext.value = false
                }
            }
            is QuestionEvent.GetPhoto -> {
                viewModelScope.launch {
                    _eventFlow.emit(
                        UIEvent.ShowCamera("ShowCamera")
                    )
                }
            }
            is QuestionEvent.NavigateToAfterQuestion -> {
                viewModelScope.launch {
                    postAnswersUseCase(answer.value.toDomain()).catch { e ->
                        Log.e("TAG", "postAnswer: ", e)
                    }.collect{

                    }
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
        data class ShowCamera(val message: String): UIEvent()
    }
}