package ru.practicum.android.diploma.domain.models

import ru.practicum.android.diploma.util.Currency

data class Vacancy(
    val id: String,
    val imageUrl: String?,
    val name: String,
    val city: String,
    val salaryFrom: Int,
    val salaryTo: Int,
    val currency: Currency,
    val employerName: String,
    val experience: String,
    val employmentName: String,
    val schedule: String
)
