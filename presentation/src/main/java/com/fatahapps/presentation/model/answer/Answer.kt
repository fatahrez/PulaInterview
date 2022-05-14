package com.fatahapps.presentation.model.answer


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.parcelize.RawValue

@Parcelize
data class Answer(
    val bitmap: String?
): Parcelable