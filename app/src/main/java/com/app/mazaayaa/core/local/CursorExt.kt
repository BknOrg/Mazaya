package com.app.mazaayaa.core.local

import android.database.Cursor
import com.app.mazaayaa.core.model.Dictionary

fun Cursor.toDictionaryList(type: String): List<Dictionary> {
    val list = mutableListOf<Dictionary>()
    val idIndex = getColumnIndexOrThrow("id")
    val wordsIndex = getColumnIndexOrThrow("words")
    val cleanWordsIndex = getColumnIndexOrThrow("clean_words")
    val meansIndex = getColumnIndexOrThrow("means")

    while (moveToNext()) {
        list.add(
            Dictionary(
                id = getInt(idIndex),
                words = getString(wordsIndex),
                cleanWords = getString(cleanWordsIndex),
                means = getString(meansIndex),
                type = type
            )
        )
    }
    return list
}