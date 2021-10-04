package com.example.loadmoredemo.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Conten1")
data class ContentEntities(
    @PrimaryKey
    @ColumnInfo(name = "ID") val id: Int,
    val image: String,
    val name: String,
    val price: Double,
    var isfavorite : Boolean
)
