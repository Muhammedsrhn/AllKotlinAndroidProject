package com.example.btksuperhero

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_tanitim.*

class TanitimActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tanitim)

        // get data from intent

        val ıntent = intent
        val superHeroName = ıntent.getStringExtra("heroName") as String
        val superHeroImage = ıntent.getIntExtra("heroImage",0)
        val bitmap = BitmapFactory.decodeResource(applicationContext.resources,superHeroImage)
        //val selectedHero = SinglentonClass.SelectedHero
        heroName.text = superHeroName
        heroImage.setImageBitmap(bitmap)







    }
}