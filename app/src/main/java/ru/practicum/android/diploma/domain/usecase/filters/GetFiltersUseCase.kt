package ru.practicum.android.diploma.domain.usecase.filters

import ru.practicum.android.diploma.domain.api.FilterRepository
import ru.practicum.android.diploma.domain.models.Filter

class GetFiltersUseCase(private val filterRepository: FilterRepository) {
    fun execute(): Filter {
        return filterRepository.getFilters()
    }
}
