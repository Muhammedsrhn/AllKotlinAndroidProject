package com.udemy.practiicekotlin

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var contactList = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            readContact(it)
        }
    }

    @SuppressLint("Range")
    fun readContact(view: View) {
        if (ActivityCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //no access

            val snack = Snackbar.make(view, "This is a simple Snackbar", Snackbar.LENGTH_LONG)
            snack.setText("You Should Giving Permissons!!")
            snack.setAction("Give Access", View.OnClickListener {
                requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), 1)
                Toast.makeText(it.context, "Tıklandı", Toast.LENGTH_SHORT).show()
            }).show()

        } else {
            //have access
            val contentResolver = contentResolver
            val projection = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)
            val cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI, projection,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME
            )
            val columnIndex = ContactsContract.Contacts.DISPLAY_NAME

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    contactList.add(cursor.getString(cursor.getColumnIndex(columnIndex)))
                }
                val adapter =
                    ArrayAdapter<String>(
                        applicationContext,
                        android.R.layout.simple_list_item_1,
                        contactList
                    )
                listView.adapter = adapter
            }


        }


    }

    @SuppressLint("Range")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1) {
            val contentResolver = contentResolver
            val projection = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)
            val cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI, projection,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME
            )
            val columnIndex = ContactsContract.Contacts.DISPLAY_NAME

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    contactList.add(cursor.getString(cursor.getColumnIndex(columnIndex)))
                }
                val adapter =
                    ArrayAdapter<String>(
                        applicationContext,
                        android.R.layout.simple_list_item_1,
                        contactList
                    )
                listView.adapter = adapter
            }

        }
    }
}