package ru.practicum.android.diploma.domain.usecase.vacancy

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.domain.api.VacancyRepository
import ru.practicum.android.diploma.domain.models.Filter

class GetVacanciesUseCase(
    private val repository: VacancyRepository
) {

    suspend fun execute(expression: String, page: Int, filter: Filter) = withContext(Dispatchers.IO) {
        repository.searchVacancies(expression = expression, page = page, filter = filter)
    }
}
