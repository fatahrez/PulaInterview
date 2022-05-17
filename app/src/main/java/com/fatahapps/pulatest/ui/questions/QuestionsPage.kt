package com.fatahapps.pulatest


import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.fatahapps.presentation.model.answer.Answer
import com.fatahapps.presentation.viewmodel.questions.GetQuestionsViewModel
import com.fatahapps.presentation.viewmodel.questions.QuestionEvent
import com.fatahapps.pulatest.destinations.CameraPageDestination
import com.fatahapps.pulatest.destinations.OnboardingPageDestination
import com.fatahapps.pulatest.ui.camera.CameraPage
import com.fatahapps.pulatest.ui.theme.BackgroundGreen
import com.fatahapps.pulatest.ui.theme.ColorButton
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Destination
@Composable
fun QuestionsPage(
    navigator: DestinationsNavigator
) {
    val viewModel: GetQuestionsViewModel = hiltViewModel()

    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()


    val activity = LocalContext.current as MainActivity

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest {
            when(it) {
                is GetQuestionsViewModel.UIEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        it.message,
                        duration = SnackbarDuration.Short
                    )
                }
                is GetQuestionsViewModel.UIEvent.ShowCamera -> {
                    activity.openCamera()
                }
                else -> {}
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState
    ) {
        if (!state.isLoading) {
            QuestionScreenSection(viewModel= viewModel, activity, navigator = navigator)
        } else {
            Box(modifier = Modifier
                .fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize(0.5f)
                        .align(Alignment.Center),
                    color = BackgroundGreen
                )
            }
        }
    }

    BackHandler(true) {
        
    }
}

@Composable
fun QuestionScreenSection(
    viewModel: GetQuestionsViewModel,
    activity: MainActivity,
    navigator: DestinationsNavigator
) {
    val stringAns = remember {
        mutableStateOf("")
    }
    val optionAns = remember {
        mutableStateOf("")
    }
    val floatAns = remember {
        mutableStateOf("")
    }
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
            color = ColorButton
        )
        Spacer(modifier = Modifier.height(48.dp))
        var question = viewModel.questionList.value[viewModel.currentQuestion]

        for ((key, value) in viewModel.stringState.value.engStrings?.iterator() ?: emptyList()) {
            if (question.id.joinStringsLowerCase() == key.lowercase()) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.h6
                )
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
                optionAns.value = selectedOption.value
            }
        }

        if (question.answerType.joinStringsLowerCase() == "singlelinetext" && question.questionType.joinStringsLowerCase() != "selectone") {
            var text by remember { mutableStateOf(TextFieldValue("")) }
            TextField(
                value = text,
                onValueChange = {
                    text = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                label = { Text("Your Name e.g. Jane Doe") },
                singleLine = true,
                placeholder = { Text("Your Name e.g. Jane Doe") },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                )
            )
            stringAns.value = text.text
        }

        if (question.answerType.joinStringsLowerCase() == "float"){
            Log.i("TAG", "QuestionScreenSection: ${question.answerType.joinStringsLowerCase()}")
            var text by remember { mutableStateOf(TextFieldValue("")) }

            TextField(
                value = text,
                onValueChange = {
                    text = it
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                label = { Text("Size in hectares e.g. 100.5") },
                singleLine = true,
                placeholder = { Text("Size in hectares e.g. 100.5") },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier
                .height(8.dp)
            )
            Spacer(modifier = Modifier
                .height(4.dp)
            )
            floatAns.value = text.text
        }

        Spacer(modifier = Modifier.height(24.dp))

        Image(
            painter = rememberImagePainter(data = activity.imageBitmap),
            contentDescription = "Farmer Image",
            modifier = Modifier
                .height(100.dp)
                .align(CenterHorizontally)
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
                val answer = Answer(activity.getPhotoPath(), listOf(stringAns.value, optionAns.value, floatAns.value))
                viewModel._answer.value = answer
                viewModel.onEvent(QuestionEvent.NavigateToAfterQuestion)
//                Toast.makeText(co, , Toast.LENGTH_SHORT).show()
                navigator.navigate(OnboardingPageDestination)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = ColorButton,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(30)
        ) {
            Text(
                text = viewModel.buttonText.value
            )
        }
    }
}