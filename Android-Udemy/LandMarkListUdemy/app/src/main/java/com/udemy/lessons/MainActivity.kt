package com.udemy.lessons

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.udemy.lessons.databinding.ActivityMainBinding
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var landArray: ArrayList<LandMark>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        landArray = ArrayList<LandMark>()

        val pisa = LandMark("Pisa", "Italya", R.drawable.pisa)
        val colosseum = LandMark("Colosseum", "Italya", R.drawable.coloseum)
        val eiffel = LandMark("Eiffel", "Fransa", R.drawable.eyfer)
        val londonBridge = LandMark("Bridge", "Ingiltere", R.drawable.bridge)

        //bu yontem verimsiz uygulama çökebilir singlenton kullanılmalı
        //val pisaBitmap = BitmapFactory.decodeResource(resources,R.drawable.pisa)

        landArray.add(pisa)
        landArray.add(colosseum)
        landArray.add(eiffel)
        landArray.add(londonBridge)

        val adapter = RecycAdapter(landArray)
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        /*
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            landArray.map { landMark -> landMark.name })
        binding.listView.adapter = adapter
        view.listView.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, itemView, i, l ->
                val intent = Intent(this, DetailsActivity::class.java)
                intent.putExtra("landmark",landArray.get(i))
                startActivity(intent)
            }

         */


    }
}