package com.example.loadmoredemo.database

import androidx.room.*
import com.example.loadmoredemo.ApiModel.Content

@Dao
interface ContentDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inSertItems(iems : ContentEntities)

    @Query("SELECT * FROM Conten1 ORDER BY ID DESC")
    fun getContent() : List<ContentEntities>

    @Query("SELECT ID FROM Conten1")
    fun getName() : List<Int>
}