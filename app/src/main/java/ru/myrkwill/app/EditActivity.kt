package ru.myrkwill.app

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import ru.myrkwill.app.databinding.ActivityEditBinding
import ru.myrkwill.app.db.DatabaseManager
import ru.myrkwill.app.db.IntentConstant

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding
    private val databaseManager = DatabaseManager(this)
    private var imageUri = "empty"

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri.toString()
        binding.imageView.setImageURI(uri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntents()
    }

    override fun onResume() {
        super.onResume()
        databaseManager.open()
    }

    override fun onDestroy() {
        super.onDestroy()
        databaseManager.close()
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
            Log.d("MyTag", "Save $title, $desc, $imageUri")
            databaseManager.insert(title, desc, imageUri)
            finish()
        }
    }

    private fun getIntents() = with(binding) {
        val i = intent

        if(i != null) {
            val title = i.getStringExtra(IntentConstant.INTENT_TITLE_KEY)
            if(title != null) {
                Log.d("MyTag", "Title $title")
                fbAddImage.visibility = View.GONE
                edTitle.setText(title)
                edDisc.setText(i.getStringExtra(IntentConstant.INTENT_DESK_KEY))
//                TODO:
//                val uri = i.getStringExtra(IntentConstant.INTENT_URI_KEY)
//                if(uri != "empty") {
//                    mainImageLayout.visibility = View.VISIBLE
//                    imButtonDeleteImage.visibility = View.GONE
//                    imButtonEditImage.visibility = View.GONE
//                    imageView.setImageURI(Uri.parse(uri))
//                }
            }
        }
    }
}