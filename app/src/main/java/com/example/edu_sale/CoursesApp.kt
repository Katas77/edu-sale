package com.example.edu_sale

import android.app.Application
import android.util.Log
import com.example.edu_sale.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CoursesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        try {
            startKoin {
                androidLogger()
                androidContext(this@CoursesApp)
                modules(allModules)
            }
            Log.d("CoursesApp", "Koin started successfully")
        } catch (e: Exception) {
            Log.e("CoursesApp", "Failed to start Koin", e)
            throw e
        }
    }
}