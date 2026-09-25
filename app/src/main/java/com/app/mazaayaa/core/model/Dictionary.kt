package com.app.mazaayaa.core.model

import android.provider.UserDictionary

data class Dictionary (
    val id: Int,
    val words: String,
    val cleanWords: String,
    val means: String,
    val type: String,
)