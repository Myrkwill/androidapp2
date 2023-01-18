package ru.myrkwill.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import ru.myrkwill.app.databinding.ActivityMainBinding
import ru.myrkwill.app.db.DatabaseManager
import ru.myrkwill.app.db.RecyclerAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val databaseManager = DatabaseManager(this)
    private val recyclerAdapter = RecyclerAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    override fun onResume() {
        super.onResume()
        databaseManager.open()
        fillAdapter()
    }

    fun onClickNew(view: View) {
        val intent = Intent(this, EditActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        databaseManager.close()
    }

    fun init() = with(binding) {
        rcView.layoutManager = LinearLayoutManager(this@MainActivity)
        rcView.adapter = recyclerAdapter
    }

    fun fillAdapter() {
        recyclerAdapter.update(databaseManager.read())
    }
}