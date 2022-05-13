package com.fatahapps.pulatest.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.fatahapps.pulatest.R

// Set of Material typography styles to start with
val latoFonts = FontFamily(
    Font(R.font.lato_bold, FontWeight.Bold),
    Font(R.font.lato_light, FontWeight.Light),
    Font(R.font.lato_regular, FontWeight.Normal),
    Font(R.font.lato_black, FontWeight.Black),
    Font(R.font.lato_thin, FontWeight.Thin)
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = latoFonts,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),

    h1 = TextStyle(
        fontFamily = latoFonts,
        fontWeight = FontWeight.Bold,
        fontSize = 50.sp
    ),

    overline = TextStyle(
        fontFamily = latoFonts,
        fontWeight = FontWeight.Light,
        fontSize = 50.sp,
        textDecoration = TextDecoration.LineThrough
    )
)