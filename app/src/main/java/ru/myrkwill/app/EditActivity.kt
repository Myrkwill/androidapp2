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

    private var id = ""
    private var isEditState = false
    private lateinit var binding: ActivityEditBinding
    private val databaseManager = DatabaseManager(this)
    private var imageUri = "empty"

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageUri = uri.toString()
            binding.imageView.setImageURI(uri)
            grantUriPermission(packageName, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
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
        imageUri = "empty"
    }

    fun onClickChooseImage(view: View) = with(binding) {
        getContent.launch("image/*")
    }

    fun onClickSave(view: View) = with(binding) {
        val title = edTitle.text.toString()
        val desc = edDisc.text.toString()

        if(title.isNotEmpty() && desc.isNotEmpty()) {
            if(isEditState) {
                Log.d("MyTag", "Update item: $title, $desc, $imageUri")
                databaseManager.updateItem(id, title, desc, imageUri)
            } else {
                Log.d("MyTag", "Save item: $title, $desc, $imageUri")
                databaseManager.insert(title, desc, imageUri)
            }
            finish()
        }
    }

    private fun getIntents() = with(binding) {
        val i = intent

        if(i != null) {
            val title = i.getStringExtra(IntentConstant.INTENT_TITLE_KEY)
            if(title != null) {
                isEditState = true
                id = i.getIntExtra(IntentConstant.INTENT_ID_KEY, 0).toString()
                edTitle.setText(title)
                edDisc.setText(i.getStringExtra(IntentConstant.INTENT_DESK_KEY))
                imageUri = i.getStringExtra(IntentConstant.INTENT_URI_KEY)!!
                if(imageUri != "empty") {
                    fbAddImage.visibility = View.GONE
                    mainImageLayout.visibility = View.VISIBLE
                    imageView.setImageURI(Uri.parse(imageUri))
                }
            }
        }
    }
}