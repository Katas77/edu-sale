package com.example.domain.usecase

import com.example.domain.model.Course
import com.example.domain.repository.CoursesRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesUseCase(
    private val repository: CoursesRepository
) {
    operator fun invoke(): Flow<List<Course>> = repository.getFavorites()
}