package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.VacancyDetails

interface DetailsRepository {
    suspend fun getVacancyDetails(id: String): Result<VacancyDetails>
}
