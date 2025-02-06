package ru.practicum.android.diploma.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.domain.api.CountryRepository
import ru.practicum.android.diploma.util.Resource

class GetCountriesUseCase(
    private val repository: CountryRepository
) {

    suspend fun execute() = withContext(Dispatchers.IO) {
        repository.getCountry()
    }
}
