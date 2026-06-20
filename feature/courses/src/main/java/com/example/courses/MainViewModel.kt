package com.example.courses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.util.UiText
import com.example.domain.model.Course
import com.example.domain.usecase.GetCoursesUseCase
import com.example.domain.usecase.ObserveCoursesWithFavoritesUseCase
import com.example.domain.usecase.SortCoursesByDateUseCase
import com.example.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MainViewModel(
    private val getCoursesUseCase: GetCoursesUseCase,
    private val observeCoursesUseCase: ObserveCoursesWithFavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val sortCoursesByDateUseCase: SortCoursesByDateUseCase
) : ViewModel() {

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<UiText?>(null)
    val errorMessage: StateFlow<UiText?> = _errorMessage.asStateFlow()

    private var originalCourses: List<Course> = emptyList()

    init {
        // Загружаем данные с небольшой задержкой, чтобы не блокировать главный поток при старте
        viewModelScope.launch {
            delay(100) // Даём UI время отрисоваться
            loadCourses()
            observeCourses()
        }
    }

    private fun observeCourses() {
        viewModelScope.launch(Dispatchers.IO) {
            observeCoursesUseCase()
                .catch { e -> _errorMessage.value = UiText.DynamicString(e.message ?: "Error") }
                .collect { courses ->
                    _courses.value = courses
                    _isLoading.value = false
                }
        }
    }

    fun loadCourses() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            getCoursesUseCase()
                .onSuccess { courses ->
                    originalCourses = courses
                    _errorMessage.value = null
                }
                .onFailure {
                    _errorMessage.value = UiText.StringResource(R.string.courses_error_network)
                }
            _isLoading.value = false
        }
    }

    fun sortByDate(descending: Boolean) {
        viewModelScope.launch(Dispatchers.Default) {
            _courses.value = sortCoursesByDateUseCase(originalCourses, descending)
        }
    }

    fun toggleFavorite(course: Course) {
        viewModelScope.launch(Dispatchers.IO) {
            toggleFavoriteUseCase(course)
        }
    }

    fun retry() = loadCourses()
}