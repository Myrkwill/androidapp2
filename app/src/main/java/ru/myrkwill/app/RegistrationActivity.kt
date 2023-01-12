package ru.myrkwill.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegistrationActivity : AppCompatActivity() {

    private val signInLauncher = registerForActivityResult( // Создали обьект авторизации экрана
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res) // Запуск самого экрана
    }

    private lateinit var database: DatabaseReference // Создали объект базы данных

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        Log.d("MyLog", "RegistrationActivity onCreate")
        database = Firebase.database.reference // Инициализация базы данных

        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        ) // список регистраций которые мы используем

        // Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build() // создали интент для экрана firebase auth
        signInLauncher.launch(signInIntent) // запустили экран firebase auth
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse // результат экрана firebase auth
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            Log.d("MyLog", "RegistrationActivity registration success ${response?.email}")
            val currentUser = FirebaseAuth.getInstance().currentUser
            currentUser?.let {
                val firebaseUser = User(currentUser.uid, currentUser.email.toString())
                Log.d("MyLog", "RegistrationActivity firebase user $firebaseUser")
                database.child("users").child(currentUser.uid).setValue(firebaseUser)
                onBackPressed()
            }
        } else {
            Log.d("MyLog", "RegistrationActivity registration failure")
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }
}