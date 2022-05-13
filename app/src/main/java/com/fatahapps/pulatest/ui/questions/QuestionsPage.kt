package com.fatahapps.pulatest

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.fatahapps.presentation.model.survey.Question
import com.fatahapps.presentation.viewmodel.questions.GetQuestionsState
import com.fatahapps.presentation.viewmodel.questions.GetQuestionsViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun QuestionsPage(
//    onItemClick: (Int) -> Unit
) {
    val viewModel: GetQuestionsViewModel = hiltViewModel()
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()


    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(state.questions.size){item ->

            key(item) {
                QuestionView(
                    question = state.questions[item],
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Divider()
        }
    }
}

@Composable
fun QuestionView(
    question: Question,
    modifier: Modifier = Modifier
) {
    Text(text = question.questionText)
}

class ItemState {

    val expanded: Boolean
        get() = _expanded.value

    private val _expanded = mutableStateOf(false)

    fun changeState() {
        _expanded.value = !_expanded.value
    }
}