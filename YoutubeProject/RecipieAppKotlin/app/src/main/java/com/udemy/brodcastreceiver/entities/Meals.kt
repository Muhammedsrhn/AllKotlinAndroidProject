package com.udemy.brodcastreceiver.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Meals")
data class Meals(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    @ColumnInfo(name = "strMeal")
    val strMeal: String?,
    @ColumnInfo(name = "strMealThumb")
    val strMealThumb: String?,
    @ColumnInfo(name = "idMeal")
    val idMeal: String?,
)