package ru.myrkwill.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ru.myrkwill.app.db.MyDBManager

class MainActivity : AppCompatActivity() {

    private val myDBManager = MyDBManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickSave(view: View) {
        myDBManager.open()
        myDBManager.insert("title", "content")

    }

    override fun onDestroy() {
        super.onDestroy()
        myDBManager.close()
    }
}