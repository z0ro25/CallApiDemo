package com.example.loadmoredemo.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities =  [ContentEntities::class] , version = 2 )
abstract class ContentDataBase : RoomDatabase() {

    abstract fun userDao(): ContentDAO

}