package ru.practicum.android.diploma.domain.models

import ru.practicum.android.diploma.util.Currency

data class VacancyDetails(
    val id: String,
    val name: String,
    val area: String,
    val address: String,
    val imageUrl: String?,
    val salaryFrom: Int,
    val salaryTo: Int,
    val currency: Currency,
    val employerName: String,
    val experience: String,
    val employmentName: String,
    val schedule: String,
    val description: String,
    val descriptionSkills: String,
    val url: String
)
