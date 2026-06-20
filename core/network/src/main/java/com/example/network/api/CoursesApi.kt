package com.example.network.api

import com.example.network.dto.CoursesResponse
import retrofit2.http.GET

interface CoursesApi {
    @GET("uc?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q&export=download")
    suspend fun getCourses(): CoursesResponse
}