package com.firebase.firebasefotoapp.view

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.firebase.firebasefotoapp.R
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_share_photo.*
import java.util.*
import kotlin.Exception

class SharePhotoActivity : AppCompatActivity() {
    var selectBitmap: Bitmap? = null
    var selectImg: Uri? = null
    val database = Firebase.storage
    val reference = database.reference
    val firestore = Firebase.firestore
    val auth = Firebase.auth

    //dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_photo)

        shareBtn.setOnClickListener {
            if (comment.text.toString() != "" && selectImg != null) {
                val dialog = ProgressDialog(this)
                dialog.show()
                dialog.setContentView(R.layout.progress)
                dialog.setCancelable(false)

                val imageName = "${UUID.randomUUID()}.jpg"
                try {
                    val uploadImage = reference.child("images").child(imageName)
                    if (selectImg != null) {
                        uploadImage.putFile(selectImg!!).addOnSuccessListener {
                            val downloadUrl = reference.child("images").child(imageName)
                            downloadUrl.downloadUrl.addOnSuccessListener {
                                val imageUrl = it.toString()
                                val userEmail = auth.currentUser!!.email.toString()
                                val userComment = comment.text.toString()
                                val date = Timestamp.now()

                                //veritabanı işlemleri

                                val postHashMap = hashMapOf<String, Any>()
                                postHashMap.put("url", imageUrl)
                                postHashMap.put("email", userEmail)
                                postHashMap.put("comment", userComment)
                                postHashMap.put("date", date)

                                firestore.collection("Posts").add(postHashMap)
                                    .addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            val intent = Intent(this, PostActivity::class.java)
                                            startActivity(intent)
                                            dialog.dismiss()
                                            Toast.makeText(
                                                this,
                                                "Published...",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            finish()
                                        } else {
                                            Toast.makeText(
                                                this,
                                                "Bir Hata Oluştu. Lütfen Tekrar Deneyin!!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }

                            }
                        }
                    } else {
                        Toast.makeText(this, "Bir Resim Secmelisiniz!!", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                Toast.makeText(this, "Eksik Alanları Doldurun!!", Toast.LENGTH_SHORT).show()
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun selectImg(view: View) {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // no access
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
            //have access
            val selectImage =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(selectImage, 2)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val selectImage =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(selectImage, 2)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            selectImg = data.data
            try {
                if (selectImg != null) {
                    //starting decode
                    if (Build.VERSION.SDK_INT >= 28) {
                        val source = ImageDecoder.createSource(this.contentResolver, selectImg!!)
                        selectBitmap = ImageDecoder.decodeBitmap(source)
                        imageView.setImageBitmap(selectBitmap)
                    } else {
                        // small api 28
                        selectBitmap =
                            MediaStore.Images.Media.getBitmap(this.contentResolver, selectImg)
                        imageView.setImageBitmap(selectBitmap)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}