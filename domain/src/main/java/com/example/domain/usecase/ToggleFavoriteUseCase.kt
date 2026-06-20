package com.example.domain.usecase

import com.example.domain.model.Course
import com.example.domain.repository.CoursesRepository

class ToggleFavoriteUseCase(
    private val repository: CoursesRepository
) {
    suspend operator fun invoke(course: Course) = repository.toggleFavorite(course)
}