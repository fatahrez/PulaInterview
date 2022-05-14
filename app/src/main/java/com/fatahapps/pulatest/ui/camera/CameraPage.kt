package com.fatahapps.pulatest.ui.camera


import android.util.Log
import androidx.compose.runtime.Composable
import com.fatahapps.presentation.model.answer.Answer
import com.ramcosta.composedestinations.annotation.Destination


@Destination
@Composable
fun CameraPage(
    answer: Answer
) {
    Log.i("TAG", "CameraPage: ${answer.bitmap}")
}

