package com.fatahapps.pulatest.ui.success

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun SuccessScreen() {
    Text(
        text = "Success",
        style = MaterialTheme.typography.h3
    )
}