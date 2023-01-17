package ru.myrkwill.app

import android.content.Intent
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

    override fun onResume() {
        super.onResume()
        myDBManager.open()
    }

    fun onClickNew(view: View) {
        val intent = Intent(this, EditActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        myDBManager.close()
    }
}