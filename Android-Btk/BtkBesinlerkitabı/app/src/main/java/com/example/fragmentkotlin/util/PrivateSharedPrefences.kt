package com.example.fragmentkotlin.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit


class PrivateSharedPrefences {

    companion object {

        private val ZAMAN = "zaman"
        private var sharedPreferences : SharedPreferences? = null


        @Volatile private var instance : PrivateSharedPrefences? = null
        private val lock = Any()
        operator fun invoke(context: Context) : PrivateSharedPrefences = instance ?: synchronized(lock) {
            instance ?: ozelSharedPreferencesYap(context).also {
                instance = it
            }
        }

        private fun ozelSharedPreferencesYap(context: Context): PrivateSharedPrefences{
            sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return PrivateSharedPrefences()
        }


    }

    fun saveTime(zaman: Long){
        sharedPreferences?.edit(commit = true){
            putLong(ZAMAN,zaman)
        }
    }

    fun getTime() = sharedPreferences?.getLong(ZAMAN,0)

}