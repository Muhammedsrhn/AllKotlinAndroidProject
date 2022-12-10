package com.udemy.countrylistudemy.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.udemy.countrylistudemy.model.Country

@Dao
interface CountryDao {
    // Data Access Object

    @Insert
    suspend fun insertAll(vararg countries:Country): List<Long>

    @Query("SELECT * FROM country")
    suspend fun getAllCountries() : List<Country>

    @Query("SELECT * FROM country WHERE uuid = :id")
    suspend fun getCountry(id:Int):Country

    @Query("DELETE FROM country")
    suspend fun deleteAllCountries()

}