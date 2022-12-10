package com.example.fragmentkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            val db = openOrCreateDatabase("testData", MODE_PRIVATE, null)
            db.execSQL("CREATE TABLE IF NOT EXISTS products (id INTEGER PRIMARY KEY,isim VARCHAR,fiyat INT)")
            //db.execSQL("INSERT INTO products (isim,fiyat) VALUES ('telefon','5000TL')")
            //db.execSQL("INSERT INTO products (isim,fiyat) VALUES ('Ayakkabı','300TL')")
            //delete
            //db.execSQL("DELETE FROM products WHERE id=1")
            //update
            //db.execSQL("UPDATE products SET isim='Ayakkab' WHERE isim LIKE '%ı'")
            val cursor = db.rawQuery("SELECT * FROM products", null)
            //val cursor = db.rawQuery("SELECT * FROM products WHERE isim LIKE 'a%'",null)
            //val cursor = db.rawQuery("SELECT * FROM products WHERE isim='ayakkabı'",null)

            val id = cursor.getColumnIndex("id")
            val isim = cursor.getColumnIndex("isim")
            val fiyat = cursor.getColumnIndex("fiyat")

            while (cursor.moveToNext()) {

                println(cursor.getInt(id))
                println(cursor.getString(isim))
                println(cursor.getString(fiyat))
            }
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}