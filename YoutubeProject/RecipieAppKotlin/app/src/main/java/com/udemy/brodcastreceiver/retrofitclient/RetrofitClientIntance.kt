package com.udemy.brodcastreceiver.retrofitclient

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClientIntance {

    //Singlenton
    companion object{
        //https://raw.githubusercontent.com/Muhammedsrhn/FoodAppJson/main/categories.json
        private var retrofit:Retrofit? = null
        private val BASE_URL = "https://raw.githubusercontent.com/"

        val retrofitInstance:Retrofit
            get(){
                if(retrofit == null){
                    retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }

                return retrofit!!
            }

    }



}