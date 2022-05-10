package com.fatahapps.presentation.viewmodel.questions

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatahapps.domain.entities.Resource
import com.fatahapps.domain.usecases.GetQuestionsUseCase
import com.fatahapps.presentation.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GetQuestionsViewModel @Inject constructor(
    private val getQuestionsUseCase: GetQuestionsUseCase
): ViewModel(){

    private val _state = mutableStateOf(GetQuestionsState())
    val state: State<GetQuestionsState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        showQuestions()
    }

    fun showQuestions() {
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
            }.launchIn(viewModelScope)
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String): UIEvent()
    }
}