package com.udemy.brodcastreceiver.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udemy.brodcastreceiver.entities.Category
import com.udemy.brodcastreceiver.dao.RecipieDao
import com.udemy.brodcastreceiver.entities.Meals

@Database(entities = [Category::class,Meals::class], version = 1, exportSchema = false)
abstract class RecipieDatabase : RoomDatabase() {

    //Singlenton mantıgı kullanıyruz cunku bu objeye her yerden aynı refesan erişilsin diye yapılıyor

    companion object{
        var recipieDatabase:RecipieDatabase? = null


        //Synchronized birden fazla yerden aynı andan erişilemez sırayla asenkron olarak erişilmelidir
        @Synchronized
        fun getDatabase(context: Context):RecipieDatabase{
            if(recipieDatabase == null){
                recipieDatabase = Room.databaseBuilder(
                    context,
                    RecipieDatabase::class.java,
                    "recipe.dp"
                ).build()
            }



            return recipieDatabase!!
        }
    }

    abstract fun recipieDao():RecipieDao


}
