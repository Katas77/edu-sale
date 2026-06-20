package com.example.edu_sale.di

import com.example.auth.presentation.LoginViewModel
import com.example.courses.MainViewModel
import com.example.database.di.databaseModule
import com.example.data.repository.CoursesRepositoryImpl
import com.example.domain.repository.CoursesRepository
import com.example.domain.usecase.GetCoursesUseCase
import com.example.domain.usecase.GetFavoritesUseCase
import com.example.domain.usecase.ObserveCoursesWithFavoritesUseCase
import com.example.domain.usecase.SortCoursesByDateUseCase
import com.example.domain.usecase.ToggleFavoriteUseCase
import com.example.favorites.FavoriteViewModel
import com.example.network.di.networkModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Repositories
    single<CoursesRepository> { CoursesRepositoryImpl(get(), get()) }

    // UseCases
    factory { GetCoursesUseCase(get()) }
    factory { GetFavoritesUseCase(get()) }
    factory { ObserveCoursesWithFavoritesUseCase(get()) }
    factory { SortCoursesByDateUseCase() }
    factory { ToggleFavoriteUseCase(get()) }

    // ViewModels
    viewModel { LoginViewModel() }
    viewModel { MainViewModel(get(), get(), get(), get()) }
    viewModel { FavoriteViewModel(get(), get()) }
}


val allModules = listOf(appModule, networkModule, databaseModule)