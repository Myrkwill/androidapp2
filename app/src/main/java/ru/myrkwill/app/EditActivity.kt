package ru.myrkwill.app

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import ru.myrkwill.app.databinding.ActivityEditBinding
import ru.myrkwill.app.db.MyDBManager

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding

    private val myDBManager = MyDBManager(this)

    private var imageUri = "empty"

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri.toString()
        binding.imageView.setImageURI(uri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        myDBManager.open()
    }

    override fun onDestroy() {
        super.onDestroy()
        myDBManager.close()
    }

    fun onClickAddImage(view: View) = with(binding) {
        mainImageLayout.visibility = View.VISIBLE
        fbAddImage.visibility = View.GONE
    }

    fun onClickDeleteImage(view: View) = with(binding) {
        mainImageLayout.visibility = View.GONE
        fbAddImage.visibility = View.VISIBLE
    }

    fun onClickChooseImage(view: View) = with(binding) {
        getContent.launch("image/*")
    }

    fun onClickSave(view: View) = with(binding) {
        val title = edTitle.text.toString()
        val desc = edDisc.text.toString()

        if(title.isNotEmpty() && desc.isNotEmpty()) {
            Log.d("MyTag", "save $title, $desc")
            myDBManager.insert(title, desc, imageUri)
        }
    }
}