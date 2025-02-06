package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.data.dto.vacancy.self.VacancyDto

data class VacanciesSearchResponse(
    val found: Int,
    val items: List<VacancyDto>,
    val pages: Int
) : Response()
