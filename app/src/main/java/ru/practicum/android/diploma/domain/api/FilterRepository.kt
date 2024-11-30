package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Filter

interface FilterRepository {

    fun getFilters(): Filter
    fun setFilters(value: Filter)

    fun getSearchFilters(): Filter

    fun setSearchFilters(value: Filter)
    fun clearFilters()

}
