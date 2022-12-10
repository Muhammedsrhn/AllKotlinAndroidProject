package com.example.btksuperhero

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //data init
        var superHeroList = ArrayList<String>()
        superHeroList.add("Batman")
        superHeroList.add("Superman")
        superHeroList.add("Iron Man")
        superHeroList.add("Aquaman")
        superHeroList.add("Spiderman")


        //verimsiz tanımlama
      /*
        val batmanBitmap = BitmapFactory.decodeResource(application.resources, R.drawable.batman)
        val supermanBitmap =
            BitmapFactory.decodeResource(application.resources, R.drawable.superman)
        val ironmanBitmap = BitmapFactory.decodeResource(application.resources, R.drawable.ironman)
        val aquamanBitmap = BitmapFactory.decodeResource(application.resources, R.drawable.aquaman)
        val spidermanBitmap =
            BitmapFactory.decodeResource(application.resources, R.drawable.spiderman)

        val superHeroBitmapList = ArrayList<Bitmap>()
        superHeroBitmapList.add(batmanBitmap)
        superHeroBitmapList.add(supermanBitmap)
        superHeroBitmapList.add(ironmanBitmap)
        superHeroBitmapList.add(aquamanBitmap)
        superHeroBitmapList.add(spidermanBitmap)
       */

        // verimli tanımlamalar

        val batman = R.drawable.batman
        val superman = R.drawable.superman
        val ironman = R.drawable.ironman
        val aquaman = R.drawable.aquaman
        val spiderman = R.drawable.spiderman

        var superHeroBitmapList = ArrayList<Int>()
        superHeroBitmapList.add(batman)
        superHeroBitmapList.add(superman)
        superHeroBitmapList.add(ironman)
        superHeroBitmapList.add(aquaman)
        superHeroBitmapList.add(spiderman)




        //Adapter
        val layoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = layoutManager

        val adapter = RecyclerAdapter(superHeroList, superHeroBitmapList)
        recyclerview.adapter = adapter

    }
}