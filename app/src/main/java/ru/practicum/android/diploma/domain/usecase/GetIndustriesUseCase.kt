package ru.practicum.android.diploma.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.domain.api.IndustryRepository

class GetIndustriesUseCase(
    private val repository: IndustryRepository
) {
    suspend fun execute() = withContext(Dispatchers.IO) {
        repository.getIndustries()
    }
}
