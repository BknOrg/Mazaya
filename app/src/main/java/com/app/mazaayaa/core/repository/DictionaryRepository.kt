package com.app.mazaayaa.core.repository

import android.content.Context
import com.app.mazaayaa.core.local.AppDatabase
import com.app.mazaayaa.core.local.toDictionaryList
import com.app.mazaayaa.core.model.Dictionary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DictionaryRepository (private val context: Context) {
    suspend fun getDictionary(table: String): List<Dictionary> = withContext(Dispatchers.IO) {
        val db = AppDatabase.getInstance(context)
        db.rawQuery("SELECT id, words, clean_words, means FROM $table ", null).use { cursor ->
            cursor.toDictionaryList(table)
        }
    }

    suspend fun getAllDictionary(): List<Dictionary> = withContext(Dispatchers.IO) {
        val ism = getDictionary("ism")
        val fiil = getDictionary("fiil")
        ism + fiil
    }

    suspend fun searchDictionary(query: String): List<Dictionary> = withContext(Dispatchers.IO) {
        val db = AppDatabase.getInstance(context)
        val selectionArgs = arrayOf("%$query%", "%$query%")
        val results = mutableListOf<Dictionary>()

        listOf("ism", "fiil").forEach { table ->
            val sql = "SELECT id, words, clean_words, means FROM $table WHERE words LIKE ? OR clean_words LIKE ?"
            db.rawQuery(sql, selectionArgs).use { cursor ->
                results.addAll(cursor.toDictionaryList(table))
            }
        }
        results
    }
}