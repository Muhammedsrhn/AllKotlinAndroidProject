package com.example.fragmentkotlin.servis

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fragmentkotlin.model.Besin

@Database(entities = arrayOf(Besin::class),version = 1)
abstract class BesinDatabase : RoomDatabase() {

    abstract fun besinDao() : BesinDAO

    //Singleton

    companion object {

        @Volatile private var instance : BesinDatabase? = null

        private val lock = Any()

        operator fun invoke(context : Context) = instance ?: synchronized(lock){
            instance ?: createDatabase(context).also {
                instance = it
            }
        }


        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            BesinDatabase::class.java,"besindatabase").build()

    }

}