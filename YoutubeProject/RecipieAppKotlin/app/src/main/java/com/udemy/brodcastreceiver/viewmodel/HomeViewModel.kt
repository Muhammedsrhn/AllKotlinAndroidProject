package com.udemy.brodcastreceiver.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.udemy.brodcastreceiver.entities.Category
import com.udemy.brodcastreceiver.database.RecipieDatabase
import com.udemy.brodcastreceiver.entities.Meals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeViewModel(application: Application) : BaseViewModel(application) {
    private val READ_STORAGE_PERM = 123
    val categoryListLiveData = MutableLiveData<List<Category>>()
    val mealListLiveData = MutableLiveData<List<Meals>>()


    fun getData(context: Context) {
        launch(Dispatchers.Default) {
            val dao = RecipieDatabase.getDatabase(context).recipieDao()

            val category = dao.getAllCategory
            val meals = dao.getAllMeals


            withContext(Dispatchers.Main) {
                categoryListLiveData.value = category
                mealListLiveData.value = meals
            }
        }
    }
}