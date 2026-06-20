package com.example.database.di

import androidx.room.Room
import com.example.database.dao.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "courses_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<AppDatabase>().favoriteDao() }
}