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

    fun read(): ArrayList<ListItem> {
        Log.d("MyTag", "Start read from Database")
        val dataList = ArrayList<ListItem>()

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
            val titleIndex = cursor.getColumnIndex(DatabaseConstant.COLUMN_NAME_TITLE)
            val contentIndex = cursor.getColumnIndex(DatabaseConstant.COLUMN_NAME_CONTENT)
            val uriIndex = cursor.getColumnIndex(DatabaseConstant.COLUMN_NAME_IMAGE_URI)
            val title = cursor.getString(titleIndex)
            val content = cursor.getString(contentIndex)
            val uri = cursor.getString(uriIndex)
            val item = ListItem()
            item.title = title.toString()
            item.desc = content.toString()
            item.uri = uri.toString()
            dataList.add(item)
        }
        cursor.close()
        Log.d("MyTag", "Start end from Database")

        return dataList
    }

    fun close() {
        databaseHelper.close()
    }
}