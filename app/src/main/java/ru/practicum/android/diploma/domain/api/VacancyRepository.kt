package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.models.VacancySearchResult

interface VacancyRepository {

    suspend fun searchVacancies(
        expression: String,
        page: Int,
        filter: Filter
    ): Result<VacancySearchResult>
}
