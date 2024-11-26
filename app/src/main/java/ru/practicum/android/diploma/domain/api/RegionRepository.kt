package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.util.Resource

interface RegionRepository {
    suspend fun getCountry(): Resource<List<Country>>
}
