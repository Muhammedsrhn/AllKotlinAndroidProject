package com.udemy.countrylistudemy.service

import com.udemy.countrylistudemy.model.Country
import io.reactivex.Single
import retrofit2.http.GET

interface CountryAPI {

    //GET,POST
    //https://raw.githubusercontent.com/atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json    @GET("atilsamancioglu/IA19-DataSetCountries/blob/master/countrydataset.json")
    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json ")
    fun getCountries():Single<List<Country>>
}