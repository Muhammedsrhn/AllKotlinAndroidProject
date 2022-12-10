package com.udemy.countrylistudemy.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class CustomSharedPrefences {

    companion object {
        private val TIME = "time"
        private var sharedFrefences: SharedPreferences? = null

        @Volatile private var instance: CustomSharedPrefences? = null
        val lock = Any()
        operator fun invoke(context: Context):CustomSharedPrefences = instance ?: synchronized(lock){
            instance ?: makeSharedPrefences(context).also {
                instance = it
            }
        }


        private fun makeSharedPrefences(context: Context):CustomSharedPrefences{
            sharedFrefences = PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPrefences()
        }

    }


    fun saveTime(time:Long){
        sharedFrefences?.edit(commit = true){
            putLong(TIME,time)
        }
    }

    fun getTime() = sharedFrefences?.getLong(TIME,0)

}