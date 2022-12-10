package com.udemy.brodcastreceiver.view

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.udemy.brodcastreceiver.R
import com.udemy.brodcastreceiver.adapter.MainCategoryAdapter
import com.udemy.brodcastreceiver.adapter.SubCategoryAdapter
import com.udemy.brodcastreceiver.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {
    var mainListAdapter = MainCategoryAdapter()
    var subListAdapter = SubCategoryAdapter()
    private lateinit var viewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //init viewmodel
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.getData(this)
        rv_main_category.layoutManager =
            LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
        rv_main_category.adapter = mainListAdapter
        rv_sub_category.layoutManager =
            LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
        rv_sub_category.adapter = subListAdapter

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.categoryListLiveData.observe(this, Observer {
            mainListAdapter.updateCategoryList(it)
        })

        viewModel.mealListLiveData.observe(this, Observer {
            subListAdapter.updateMealList(it)

        })
    }
}