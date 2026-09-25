package com.app.mazaayaa.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.app.mazaayaa.R

// Set of Material typography styles to start with
val Scheherazade = FontFamily(
    Font(R.font.scheherazade_new_medium, FontWeight.Normal)
)
val ArabicTextStyle = TextStyle(
    fontFamily = Scheherazade,
    fontSize = 28.sp,
    lineHeight = 36.sp
)