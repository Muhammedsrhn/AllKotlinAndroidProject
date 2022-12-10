package com.firebase.firebasefotoapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.firebasefotoapp.R
import com.firebase.firebasefotoapp.adapter.PostListAdapter
import com.firebase.firebasefotoapp.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseFirestore
    private lateinit var adapter: PostListAdapter
    var postList = ArrayList<Post>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        auth = Firebase.auth
        database = Firebase.firestore

        adapter = PostListAdapter(postList)
        val manager = LinearLayoutManager(this)
        postRcylerView.layoutManager = manager
        postRcylerView.adapter = adapter
        getData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = MenuInflater(this).inflate(R.menu.menu_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.photoShare -> {
                val intent = Intent(this, SharePhotoActivity::class.java)
                startActivity(intent)
            }
            R.id.logout -> {
                auth.signOut()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun getData() {
        database.collection("Posts").orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
                } else {
                    if (snapshot != null) {
                        if (!snapshot.isEmpty) {

                            val docList = snapshot.documents
                            postList.clear()

                            for (list in docList) {
                                val email = list.get("email") as String
                                val comment = list.get("comment") as String
                                val url = list.get("url") as String

                                val downloadPost = Post(email,comment,url)
                                postList.add(downloadPost)
                            }
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            }

    }
}