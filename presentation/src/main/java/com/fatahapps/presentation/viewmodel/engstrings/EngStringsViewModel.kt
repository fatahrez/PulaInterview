package com.fatahapps.presentation.viewmodel.engstrings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatahapps.domain.entities.Resource
import com.fatahapps.domain.usecases.GetStringsUseCase
import com.fatahapps.presentation.mapper.toPresentation
import com.fatahapps.presentation.model.survey.EngStrings
import com.fatahapps.presentation.viewmodel.questions.GetQuestionsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EngStringsViewModel @Inject constructor(
    private val getStringsUseCase: GetStringsUseCase
): ViewModel() {

    private val _state = mutableStateOf(EngStringsState())
    val state: State<EngStringsState> = _state

    private val _eventFlow = MutableSharedFlow<GetQuestionsViewModel.UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        showStrings()
    }

    fun showStrings() {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoading = true
            )
            getStringsUseCase().onEach{result ->
                when(result) {
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            engStrings = result.data?.toPresentation(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _eventFlow.emit(
                            GetQuestionsViewModel.UIEvent.ShowSnackbar(
                                result.message ?: "Unknown Error"
                            )
                        )
                        _state.value = state.value.copy(
                            isLoading = false,
                            engStrings = result.data?.toPresentation()
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
    }

}