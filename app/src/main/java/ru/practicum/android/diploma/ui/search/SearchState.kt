package ru.practicum.android.diploma.ui.search

import ru.practicum.android.diploma.domain.models.Vacancy

data class SearchState(
    val input: Input,
    val vacanciesList: VacanciesList,
) {

    sealed interface Input {
        data object Empty : Input
        data class Text(val value: String) : Input
    }

    sealed interface VacanciesList {
        data object Start : VacanciesList
        data object NoInternet : VacanciesList
        data object Empty : VacanciesList
        data object Loading : VacanciesList
        data object Error : VacanciesList
        data class Data(val vacancies: List<Vacancy>, val totalVacancyCount: Int) : VacanciesList
    }
}
