package com.udemy.brodcastreceiver.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// isimler aynı geldiig icin Serializable yapmaya gerekyok
@Entity
data class Category (
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    @ColumnInfo(name = "idCategory")
    val idCategory: String?,
    @ColumnInfo(name = "strCategory")
    val strCategory: String?,
    @ColumnInfo(name = "strCategoryThumb")
    val strCategoryThumb: String?,
    @ColumnInfo(name = "strCategoryDescription")
    val strCategoryDescription: String?
)
