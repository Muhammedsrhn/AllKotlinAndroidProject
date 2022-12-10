package com.udemy.brodcastreceiver.interfaces

import com.udemy.brodcastreceiver.entities.Category
import com.udemy.brodcastreceiver.entities.Meals
import retrofit2.Call
import retrofit2.http.GET

interface GetDataService {
    //https://raw.githubusercontent.com/Muhammedsrhn/FoodAppJson/main/categories.json
    //https://raw.githubusercontent.com/Muhammedsrhn/FoodAppJson/main/meals.json
    @GET("Muhammedsrhn/FoodAppJson/main/categories.json")
    fun getCategoryList(): Call<List<Category>>

    @GET("Muhammedsrhn/FoodAppJson/main/meals.json")
    fun getMealList(): Call<List<Meals>>
}