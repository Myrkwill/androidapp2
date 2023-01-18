package ru.myrkwill.app.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log

class DatabaseManager(val context: Context) {

    val databaseHelper = DatabaseHelper(context)
    var db: SQLiteDatabase? = null

    fun open() {
        db = databaseHelper.writableDatabase
    }

    fun insert(title: String, content: String, uri: String) {
        val values = ContentValues().apply {
            put(DatabaseConstant.COLUMN_NAME_TITLE, title)
            put(DatabaseConstant.COLUMN_NAME_CONTENT, content)
            put(DatabaseConstant.COLUMN_NAME_IMAGE_URI, uri)
        }
        Log.d("MyTag", "save in $db")
        db?.insert(DatabaseConstant.TABLE_NAME, null, values)
    }

    fun read(): ArrayList<String> {
        val dataList = ArrayList<String>()

        val cursor = db?.query(
            DatabaseConstant.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        while (cursor?.moveToNext()!!) {
            val index = cursor.getColumnIndex(DatabaseConstant.COLUMN_NAME_TITLE)
            val dataText = cursor.getString(index)
            Log.d("MyTag", "${dataText.toString()}")
            dataList.add(dataText.toString())
        }
        cursor.close()

        Log.d("MyTag", "${dataList}")

        return dataList
    }

    fun close() {
        databaseHelper.close()
    }
}