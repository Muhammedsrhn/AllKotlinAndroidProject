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
import com.udemy.mychatapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
    }

    fun checkEdtText() {
        if (binding.etName.text.toString() == "" ||
            binding.etEmail.text.toString() == "" ||
            binding.etConfirmPassword.text.toString() == ""
        ) {
            Toast.makeText(this, "Please Fill The All Area", Toast.LENGTH_SHORT).show()
        }

        if (binding.password.text.toString() != binding.etConfirmPassword.text.toString()) {
            Toast.makeText(this, "Password Fields Do Not Match", Toast.LENGTH_SHORT).show()
        }


    }


    fun signUp(view: View) {
        checkEdtText()
        try {
            auth.createUserWithEmailAndPassword(
                binding.etEmail.text.toString(),
                binding.etConfirmPassword.text.toString()
            ).addOnSuccessListener {
                addDataToFrebase()
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            e.localizedMessage
        }
    }

    fun addDataToFrebase() {

        val userMap = HashMap<String, Any>()


        auth.currentUser?.let {
            database = Firebase.database
            userMap.put("userId", auth.currentUser!!.uid)
            userMap.put("username", binding.etName.text.toString())
            userMap.put("userProfile", "")
            database.getReference("Users").child(auth.currentUser!!.uid)
                .setValue(userMap).addOnCompleteListener {

            }.addOnFailureListener {
                it.printStackTrace()
            }


        }!!.addOnFailureListener {
            Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}