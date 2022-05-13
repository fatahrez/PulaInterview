package com.fatahapps.pulatest

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fatahapps.presentation.model.survey.Question
import com.fatahapps.presentation.viewmodel.questions.GetQuestionsState
import com.fatahapps.presentation.viewmodel.questions.GetQuestionsViewModel
import com.fatahapps.presentation.viewmodel.questions.QuestionEvent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest

@Destination
@Composable
fun QuestionsPage(
    navigator: DestinationsNavigator
) {
    val viewModel: GetQuestionsViewModel = hiltViewModel()
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest {
            when(it) {
                is GetQuestionsViewModel.UIEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        it.message,
                        duration = SnackbarDuration.Short
                    )
                }
                else -> {}
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState
    ) {
        if (!state.isLoading) {
            Log.d("Questions", "QuestionsPage: ${viewModel.questionList.value}")
            QuestionScreenSection(viewModel= viewModel)
        }
    }
}

@Composable
fun QuestionScreenSection(
    viewModel: GetQuestionsViewModel
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "${viewModel.currentQuestion + 1} of ${viewModel.questionCount.value}",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(15.dp))
        LinearProgressIndicator(
            progress = viewModel.progressIndicator.value,
            modifier = Modifier
                .fillMaxWidth(),
            color = Color.Blue
        )
        Text(
            text = viewModel.questionList.value[viewModel.currentQuestion].id
        )
        
        Button(onClick = {
            if (viewModel.currentQuestion + 1 != viewModel.questionCount.value) {
//                if (viewModel.questionIsSelected.value) {
                    viewModel.currentQuestion++
                    viewModel.onEvent(QuestionEvent.ProgressIndicator)
                    viewModel.onEvent(QuestionEvent.NextQuestion)
//                } else {
//                    viewModel.questionNotSelected()
//                }
            } else {
                Log.d("Question size", "QuestionScreenSection: ${viewModel.currentQuestion}, size: ${viewModel.questionList.value.size}")
                viewModel.onEvent(QuestionEvent.NavigateToAfterQuestion)
            }
        }) {
            Text(
                text = viewModel.buttonText.value
            )
        }
    }
}
