package ru.myrkwill.app.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log

class MyDBManager(val context: Context) {

    val myDBHelper = MyDBHelper(context)
    var db: SQLiteDatabase? = null

    fun open() {
        db = myDBHelper.writableDatabase
    }

    fun insert(title: String, content: String, uri: String) {
        val values = ContentValues().apply {
            put(MyDBNameClass.COLUMN_NAME_TITLE, title)
            put(MyDBNameClass.COLUMN_NAME_CONTENT, content)
            put(MyDBNameClass.COLUMN_NAME_IMAGE_URI, uri)
        }
        Log.d("MyTag", "save in $db")
        db?.insert(MyDBNameClass.TABLE_NAME, null, values)
    }

    fun read(): ArrayList<String> {
        val dataList = ArrayList<String>()

        val cursor = db?.query(
            MyDBNameClass.DATABASE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        while (cursor?.moveToNext()!!) {
            val index = cursor.getColumnIndex(MyDBNameClass.COLUMN_NAME_TITLE)
            val dataText = cursor.getString(index)
            dataList.add(dataText.toString())
        }
        cursor.close()

        return dataList
    }

    fun close() {
        myDBHelper.close()
    }
}