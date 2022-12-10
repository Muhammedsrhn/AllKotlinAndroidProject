package com.udemy.practiicekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_join.setOnClickListener {
            if(edit_room.text.toString() == ""){
                Toast.makeText(this,"Lütfen Bir Id Girin..",Toast.LENGTH_SHORT).show()
            }else{
                val int = Intent(this,VideoCallActivity::class.java)
                int.putExtra("callId",edit_room.text.toString())
                startActivity(int)
            }

        }

    }


}