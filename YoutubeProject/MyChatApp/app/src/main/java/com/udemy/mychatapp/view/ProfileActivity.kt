package com.udemy.mychatapp.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.udemy.mychatapp.databinding.ActivityProfileBinding
import com.udemy.mychatapp.model.Profile
import com.udemy.mychatapp.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var bitmap: Bitmap
    private var source: Uri? = null
    var img: Any? = ""
    var uuid = ""
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        source = null
        database = Firebase.database
        auth = Firebase.auth
        uuid = auth.currentUser!!.uid
        storage = Firebase.storage
        getProfileDetails()

        storage.getReference("images")
            .child("${Firebase.auth.currentUser!!.uid}.jpg").downloadUrl.addOnSuccessListener {
                if (it != null) {
                    img = it
                }
            }

        binding.userImage.setOnClickListener {
            choseImage()
        }
        binding.btnUpdate.setOnClickListener {
            if (source != null) {
                storage.getReference("images").child("${Firebase.auth.currentUser!!.uid}.jpg")
                    .putFile(source!!)
                updateProfile()
            }


            updateProfile()

        }
        binding.imgBack.setOnClickListener {
            finish()
        }
    }


    private fun choseImage() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // no access
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
            // we have access
            val image = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(image, 2)
        }
    }

    private fun getProfileDetails() {
        try {
            database.getReference("Profiles").child(uuid).get().addOnCompleteListener {
                if (it.isSuccessful) {
                    if (it.result.exists()) {
                        val profile = it.result.getValue(Profile::class.java)
                        profile?.let {
                            binding.etName.setText(profile.username)
                            binding.etSurname.setText(profile.surname)
                            binding.etAge.setText(profile.age)
                            binding.etHobby.setText(profile.hobbys)
                            binding.etStatus.setText(profile.status)
                            storage.getReference("images")
                                .child(Firebase.auth.currentUser!!.uid + ".jpg").downloadUrl.addOnSuccessListener {
                                    if (it != null) {
                                        Glide.with(applicationContext).load(it)
                                            .into(binding.userImage)
                                    }
                                }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.localizedMessage
        }
    }

    private fun updateProfile() {
        try {



            CoroutineScope(Dispatchers.IO).launch {
                delay(2000)
                val profilesMap = HashMap<String, Any>()
                profilesMap.put("userId", uuid)
                profilesMap.put("image", img.toString())
                profilesMap.put("username", binding.etName.text.toString())
                profilesMap.put("surname", binding.etSurname.text.toString())
                profilesMap.put("age", binding.etAge.text.toString())
                profilesMap.put("hobbys", binding.etHobby.text.toString())
                profilesMap.put("status", binding.etStatus.text.toString())
                database.getReference("Profiles").child(uuid).setValue(profilesMap)
                    .addOnSuccessListener {
                        Toast.makeText(this@ProfileActivity, "Profile Updated", Toast.LENGTH_LONG)
                            .show()
                    }
                delay(4000)
                storage.getReference("images")
                    .child("${Firebase.auth.currentUser!!.uid}.jpg").downloadUrl.addOnSuccessListener {
                        if (it != null) {
                            img = it
                        }
                    }
                database.getReference("Users").child(uuid).get().addOnCompleteListener {
                    val user = it.result.getValue(User::class.java)
                    user?.let {
                        val userId = user.userId
                        val username = binding.etName.text.toString()
                        database.getReference("Profiles").child(uuid).get().addOnCompleteListener {
                            val profile = it.result.getValue(Profile::class.java)
                            img = profile!!.image
                        }
                        val newUserMap = HashMap<String, Any>()
                        newUserMap.put("userId", userId.toString())
                        newUserMap.put("username", username)
                        newUserMap.put("userProfile", img.toString())
                        database.getReference("Users").child(uuid).setValue(newUserMap)
                            .addOnFailureListener {
                                Toast.makeText(
                                    applicationContext,
                                    it.localizedMessage,
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            }
                    }

                }
            }


        } catch (e: Exception) {
            e.localizedMessage
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val image = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(image, 2)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data!!.data != null) {
            source = data.data!!
            if (Build.VERSION.SDK_INT >= 28) {
                val source = ImageDecoder.createSource(this.contentResolver, data.data!!)
                bitmap = ImageDecoder.decodeBitmap(source)
                binding.userImage.setImageBitmap(bitmap)
            } else {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, data.data)
                binding.userImage.setImageBitmap(bitmap)
            }
        }


    }
}