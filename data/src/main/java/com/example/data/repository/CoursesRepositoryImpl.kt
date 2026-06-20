package com.example.data.repository

import com.example.database.dao.FavoriteDao
import com.example.database.entity.FavoriteCourseEntity
import com.example.domain.model.Course
import com.example.domain.repository.CoursesRepository
import com.example.network.api.CoursesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CoursesRepositoryImpl(
    private val api: CoursesApi,
    private val favoriteDao: FavoriteDao
) : CoursesRepository {

    private val _apiCourses = MutableStateFlow<List<Course>>(emptyList())

    override suspend fun getCourses(): Result<List<Course>> = withContext(Dispatchers.IO) {
        try {
            val response = api.getCourses()
            val courses = response.courses.map { it.toDomain() }
            _apiCourses.value = courses
            val favoriteIds = favoriteDao.getFavoriteIds().toSet()
            val updated = courses.map { it.copy(hasLike = it.id in favoriteIds) }
            Result.success(updated)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun observeCoursesWithFavorites(): Flow<List<Course>> = combine(
        _apiCourses,
        favoriteDao.getFavorites()
    ) { courses, favorites ->
        val favoriteIds = favorites.map { it.courseId }.toSet()
        courses.map { it.copy(hasLike = it.id in favoriteIds) }
    }.flowOn(Dispatchers.IO)

    override suspend fun toggleFavorite(course: Course) = withContext(Dispatchers.IO) {
        val entity = course.toEntity()
        if (favoriteDao.isFavorite(course.id)) {
            favoriteDao.removeFromFavorites(entity)
        } else {
            favoriteDao.addToFavorites(entity)
        }
    }

    override suspend fun isFavorite(courseId: Int): Boolean = withContext(Dispatchers.IO) {
        favoriteDao.isFavorite(courseId)
    }

    override fun getFavorites(): Flow<List<Course>> = favoriteDao.getFavorites().map { entities ->
        entities.map { it.toDomain() }
    }.flowOn(Dispatchers.IO)

    private fun Course.toEntity(): FavoriteCourseEntity = FavoriteCourseEntity(
        courseId = id,
        title = title,
        text = text,
        price = price,
        rate = rate,
        startDate = startDate,
        publishDate = publishDate
    )

    private fun FavoriteCourseEntity.toDomain(): Course = Course(
        id = courseId,
        title = title,
        text = text,
        price = price,
        rate = rate,
        startDate = startDate,
        hasLike = true,
        publishDate = publishDate
    )
}