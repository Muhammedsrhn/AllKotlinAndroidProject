package com.firebase.firebasefotoapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.firebase.firebasefotoapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        if (auth.currentUser != null) {
            val intent = Intent(this, PostActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun signIn(view: View) {
        if (email.text.toString() != "" && password.text.toString().length >= 6) {
            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, PostActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Login Error!", Toast.LENGTH_LONG).show()
                    }
                }.addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Bir Hata Oluştu", Toast.LENGTH_LONG).show()
        }
    }

    fun signUp(view: View) {
        if (email.text.toString() != "" && password.text.toString().length >= 6) {
            auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, PostActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Register Error!", Toast.LENGTH_LONG).show()
                    }
                }.addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Bir Hata Oluştu", Toast.LENGTH_LONG).show()
        }


    }
}