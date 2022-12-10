package com.udemy.mychatapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.udemy.mychatapp.adapter.UserAdapter
import com.udemy.mychatapp.databinding.ActivityUsersBinding
import com.udemy.mychatapp.model.Profile
import com.udemy.mychatapp.model.User

class UsersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUsersBinding
    private lateinit var adapter: UserAdapter
    private lateinit var database: FirebaseDatabase
    private var uuid = ""
    var userList = ArrayList<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        uuid = Firebase.auth.currentUser!!.uid
        database = Firebase.database
        binding.imgBack.setOnClickListener {
            finish()
        }

        binding.imgProfile.setOnClickListener {
            val intent = Intent(applicationContext, ProfileActivity::class.java)
            startActivity(intent)
        }
        getUsersList()
    }

    fun getUsersList() {
        val firebase = Firebase.auth.currentUser
        val reference = Firebase.database

        reference.getReference("Profiles").child(firebase!!.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val profile = snapshot.getValue(Profile::class.java)
                        profile?.let {
                            Glide.with(this@UsersActivity).load(profile.image).into(binding.imgProfile)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@UsersActivity, error.message, Toast.LENGTH_LONG).show()
                }

            })

        reference.getReference("Users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    userList.clear()
                    for (children in snapshot.children) {

                        val user = children.getValue(User::class.java)
                        //resim yoksa default resim kullan
                        if (user!!.userProfile == "") {
                            user.userProfile =
                                "https://images.nightcafe.studio//assets/profile.png?tr=w-1600,c-at_max"
                        }
                        if (user.userId.toString() != firebase.uid) {
                            userList.add(user)
                        }
                    }

                    adapter = UserAdapter(userList)
                    val manager = LinearLayoutManager(applicationContext)
                    binding.userRecyclerView.adapter = adapter
                    binding.userRecyclerView.layoutManager = manager
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_LONG).show()
            }

        })

    }

}