package com.app.mazaayaa.core.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream


object AppDatabase {
    private const val DB_NAME = "mufrodat.db"
    private const val ASSET_PATH = "database/$DB_NAME"

    @Volatile
    private var database: SQLiteDatabase? = null

    suspend fun getInstance(context: Context): SQLiteDatabase = withContext(Dispatchers.IO) {
        database?.let { if (it.isOpen) return@withContext it }

        synchronized(this) {
            database?.let { if (it.isOpen) return@synchronized it }
            val dbFile = context.getDatabasePath(DB_NAME)
            if (!dbFile.exists()) {
                copyDatabaseFromAssets(context, dbFile)
            }
            val db = SQLiteDatabase.openDatabase(dbFile.path, null, SQLiteDatabase.OPEN_READWRITE)
            database = db
            db
        }
    }

    private fun copyDatabaseFromAssets(context: Context, destFile: File) {
        destFile.parentFile?.let { if (!it.exists()) it.mkdirs() }

        context.assets.open(ASSET_PATH).use { input ->
            FileOutputStream(destFile).use { output ->
                input.copyTo(output)
            }
        }
    }
}