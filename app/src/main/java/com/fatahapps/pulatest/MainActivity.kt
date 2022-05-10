package com.fatahapps.pulatest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.fatahapps.presentation.model.survey.Question
import com.fatahapps.presentation.viewmodel.questions.GetQuestionsViewModel
import com.fatahapps.pulatest.ui.theme.PulaTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            PulaTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val viewModel: GetQuestionsViewModel = hiltViewModel()
                    val state = viewModel.state.value
                    QuestionsResponse(state.questions)
                }
            }
        }
    }
}

@Composable
fun QuestionsResponse(questions: List<Question>) {
    Text(text = questions.toString())
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PulaTestTheme {

    }
}