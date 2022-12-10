package com.udemy.lessons

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.udemy.lessons.databinding.ActivityDetailsBinding
import com.udemy.lessons.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_details.view.*

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //casting
        //val landmark = intent.getSerializableExtra("landmark") as LandMark

        Singleton.chosenLandMark?.let {
            view.lName.text = it.name
            view.lcountry.text = it.country
            view.imageView.setImageResource(it.image)
        }

    }
}