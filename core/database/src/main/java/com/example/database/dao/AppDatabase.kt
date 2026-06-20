package com.example.database.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.entity.FavoriteCourseEntity


@Database(entities = [FavoriteCourseEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}