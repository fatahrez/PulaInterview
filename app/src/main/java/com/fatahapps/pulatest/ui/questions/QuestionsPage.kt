package com.fatahapps.pulatest


import android.net.Uri
import android.provider.Settings.Global.getString
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fatahapps.presentation.viewmodel.questions.GetQuestionsViewModel
import com.fatahapps.presentation.viewmodel.questions.QuestionEvent
import com.fatahapps.pulatest.destinations.CameraPageDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private lateinit var outputDirectory: File
private lateinit var cameraExecutor: ExecutorService

@Destination
@Composable
fun QuestionsPage(
    navigator: DestinationsNavigator
) {
    val viewModel: GetQuestionsViewModel = hiltViewModel()

    var shouldShowCamera: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    lateinit var photoUri: Uri
    var shouldShowPhoto: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }

    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    var question: String

    fun handleImageCapture(uri: Uri) {
        Log.i("kilo", "Image captured: $uri")
        shouldShowCamera.value = false

        photoUri = uri
        shouldShowPhoto.value = true
    }

    fun getOutputDirectory(): File {
        val mediaDir = MainActivity().externalMediaDirs.firstOrNull()?.let {
            File(it, "PulaTest").apply { mkdirs() }
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else MainActivity().filesDir
    }

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
            Log.d("En Strings", "QuestionsPage: ${viewModel.stringState.value.engStrings}")
            QuestionScreenSection(viewModel= viewModel,
                shouldShowCamera.value,
                navigator = navigator,
                handleCamera = ::handleImageCapture
            )
        }
    }

    outputDirectory = getOutputDirectory()
    cameraExecutor = Executors.newSingleThreadExecutor()

    BackHandler(true) {
        
    }

    fun onDestroy() {
        cameraExecutor.shutdown()
    }

}

@Composable
fun QuestionScreenSection(
    viewModel: GetQuestionsViewModel,
    shouldShowCamera: Boolean,
    navigator: DestinationsNavigator,
    handleCamera: (Uri) -> Unit
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
        var question = viewModel.questionList.value[viewModel.currentQuestion]

        for ((key, value) in viewModel.stringState.value.engStrings?.iterator() ?: emptyList()) {
            if (question.id.joinStringsLowerCase() == key.lowercase()) {
                Text(text = value)
            }
        }


        if (question.questionType.joinStringsLowerCase() == "selectone") {
            val radioOptions = question.options
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

            Column() {
                question.options.forEach{ text ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (text == selectedOption),
                                onClick = { onOptionSelected(text) }
                            )
                            .padding(horizontal = 16.dp)
                    ) {
                        val context = LocalContext.current

                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = {
                                onOptionSelected(text)
                            },
                            modifier = Modifier.padding(all = Dp(value = 8F))
                        )

                        Text(
                            text = text.value,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        }

        if (question.answerType.joinStringsLowerCase() == "singlelinetext" && question.questionType.joinStringsLowerCase() != "selectone") {
            var text by remember { mutableStateOf(TextFieldValue("")) }
            TextField(
                value = text,
                onValueChange = {
                    text = it
                }
            )
        }

        if (question.answerType.joinStringsLowerCase() == "float"){
            Log.i("TAG", "QuestionScreenSection: ${question.answerType.joinStringsLowerCase()}")
            var text by remember { mutableStateOf(TextFieldValue("")) }

            TextField(
                value = text,
                onValueChange = {
                    text = it
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        }

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
                viewModel.onEvent(QuestionEvent.NavigateToAfterQuestion)
                if (shouldShowCamera) {
                    navigator.navigate(
                        CameraPageDestination(
                            outputDirectory = outputDirectory,
//                            cameraExecutor,
//                          ::handleImageCapture,
//                            onError = { Log.e("TAG", "QuestionScreenSection: ")}
                        )
                    )
                }
            }
        }) {
            Text(
                text = viewModel.buttonText.value
            )
        }
    }
}
