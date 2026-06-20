package com.example.domain.repository

import com.example.domain.model.Course
import kotlinx.coroutines.flow.Flow

interface CoursesRepository {
    suspend fun getCourses(): Result<List<Course>>
    fun observeCoursesWithFavorites(): Flow<List<Course>>
    suspend fun toggleFavorite(course: Course)
    suspend fun isFavorite(courseId: Int): Boolean
    fun getFavorites(): Flow<List<Course>>
}