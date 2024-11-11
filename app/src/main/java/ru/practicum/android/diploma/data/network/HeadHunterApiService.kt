package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import ru.practicum.android.diploma.app.NavigationJobApp
import ru.practicum.android.diploma.data.dto.VacancySearchResponse

interface HeadHunterApiService {

    @GET("/vacancies")
    suspend fun searchVacancyRequest(
        @Header("Bearer") token: String = NavigationJobApp.HEAD_HUNTER_TOKEN,
        @Query("text") text: String,
        @Query("page") page: String,
        @Query("per_page") perPage: String = "20"
    ): VacancySearchResponse

}
