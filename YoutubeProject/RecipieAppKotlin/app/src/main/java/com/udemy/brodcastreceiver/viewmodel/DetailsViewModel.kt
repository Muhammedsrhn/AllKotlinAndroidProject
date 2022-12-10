package com.udemy.brodcastreceiver.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.udemy.brodcastreceiver.entities.Category
import com.udemy.brodcastreceiver.database.RecipieDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(application: Application) : BaseViewModel(application) {
    var categoryLiveData = MutableLiveData<Category>()

    fun getCategoryFromRoom(id:String?,context:Context){
      launch(Dispatchers.Default){
           val dao = RecipieDatabase.getDatabase(context).recipieDao()
           val category = dao.getOneCategory(id)
           withContext(Dispatchers.Main){
               categoryLiveData.value = category
           }
       }
    }


}