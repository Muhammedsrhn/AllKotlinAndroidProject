package com.udemy.mychatapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.udemy.mychatapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        if(auth.currentUser != null){
            val intent = Intent(this, UsersActivity::class.java)
            startActivity(intent)
            finish()
        }

        database = Firebase.database


        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }



    fun signIn(view: View) {
        checkEdtText()
    }


    private fun updateProfile() {
        try {

            val profilesMap = HashMap<String, Any>()
            profilesMap.put("userId", "")
            profilesMap.put("username", "")
            profilesMap.put("image", "https://images.nightcafe.studio//assets/profile.png?tr=w-1600,c-at_max")
            profilesMap.put("surname", "")
            profilesMap.put("age", "")
            profilesMap.put("hobbys", "")
            profilesMap.put("status", "")

            database.getReference("Profiles").child(Firebase.auth.currentUser!!.uid)
                .setValue(profilesMap)
                .addOnFailureListener {
                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                }

        } catch (e: Exception) {
            e.localizedMessage
        }
    }


    fun checkEdtText() {
        if (binding.etEmail.text.toString() == "" ||
            binding.etEmail.text.toString() == "" ||
            binding.etPassword.text.toString() == ""
        ) {
            Toast.makeText(this, "Please Fill The All Area", Toast.LENGTH_SHORT).show()
        }else{
            auth.signInWithEmailAndPassword(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            ).addOnSuccessListener {
                updateProfile()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }
}