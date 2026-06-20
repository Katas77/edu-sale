package com.example.network.di

import com.example.network.api.CoursesApi
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    single { GsonBuilder().setLenient().create() }
    single {
        Retrofit.Builder()
            .baseUrl("https://drive.google.com/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
            .create(CoursesApi::class.java)
    }
}