package com.example.domain.usecase


import com.example.common.util.DateMapper
import com.example.domain.model.Course

class SortCoursesByDateUseCase {
    operator fun invoke(courses: List<Course>, descending: Boolean): List<Course> {
        return if (descending) {
            courses.sortedByDescending { DateMapper.toMillis(it.publishDate) }
        } else {
            courses.sortedBy { DateMapper.toMillis(it.publishDate) }
        }
    }
}