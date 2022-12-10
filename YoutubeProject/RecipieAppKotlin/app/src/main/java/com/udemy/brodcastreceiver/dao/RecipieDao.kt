package com.udemy.brodcastreceiver.dao

import androidx.room.*
import com.udemy.brodcastreceiver.entities.Category
import com.udemy.brodcastreceiver.entities.Meals

@Dao
interface RecipieDao {
    @get:Query("SELECT * FROM category")
    val getAllCategory : List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(category: Category?)

    @Query("DELETE FROM category")
    suspend fun clearCategoryDb()

    @Query("SELECT * FROM Category WHERE id = :id")
    fun getOneCategory(id:String?) : Category

    //Meals
    @get:Query("SELECT * FROM Meals")
    val getAllMeals : List<Meals>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeal(category:Meals)

    @Query("DELETE FROM Meals")
    suspend fun clearMealDb()




}