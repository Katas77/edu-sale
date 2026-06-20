package com.example.domain.usecase

import com.example.domain.model.Course
import com.example.domain.repository.CoursesRepository

class GetCoursesUseCase(
    private val repository: CoursesRepository
) {
    suspend operator fun invoke(): Result<List<Course>> = repository.getCourses()
}