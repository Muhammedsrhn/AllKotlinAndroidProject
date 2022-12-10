package com.udemy.brodcastreceiver.view


import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.udemy.brodcastreceiver.R
import com.udemy.brodcastreceiver.databinding.ActivityDetailsBinding
import com.udemy.brodcastreceiver.viewmodel.DetailsViewModel
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : BaseActivity() {
    private lateinit var viewModel: DetailsViewModel
    private lateinit var binding:ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_details)

        imgToolbarBtnBack.setOnClickListener {
            finish()
        }

        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        val id = intent.getStringExtra("id")
        viewModel.getCategoryFromRoom(id, this)
        observeLiveData()

    }

    private fun observeLiveData() {
        viewModel.categoryLiveData.observe(this, Observer { category ->
            /*
             tvCategory.text = category.strCategory
             tvIngredients.text = category.strCategoryDescription
             Glide.with(this@DetailsActivity).load(category.strCategoryThumb).into(imgItem)
             */
            binding.category = category

        })
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}