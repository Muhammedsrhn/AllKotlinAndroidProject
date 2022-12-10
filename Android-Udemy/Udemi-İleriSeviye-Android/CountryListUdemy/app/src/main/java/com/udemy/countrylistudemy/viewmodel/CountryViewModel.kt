package com.udemy.countrylist.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.udemy.countrylistudemy.model.Country
import com.udemy.countrylistudemy.service.CountryDatabase
import com.udemy.countrylistudemy.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class CountryViewModel(application: Application):BaseViewModel(application) {

    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom(uuid: Int){
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            val country = dao.getCountry(uuid)
            countryLiveData.value = country
        }
    }
}