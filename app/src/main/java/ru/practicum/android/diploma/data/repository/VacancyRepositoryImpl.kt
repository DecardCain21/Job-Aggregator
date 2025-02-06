package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.mapper.OptionMapper
import ru.practicum.android.diploma.data.mapper.VacancyMapper
import ru.practicum.android.diploma.data.network.client.VacancyNetworkClient
import ru.practicum.android.diploma.domain.api.VacancyRepository
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.models.VacancySearchResult

class VacancyRepositoryImpl(
    private val vacancyNetworkClient: VacancyNetworkClient,
    private val vacancyMapper: VacancyMapper,
    private val optionMapper: OptionMapper,
) : VacancyRepository {

    override suspend fun searchVacancies(
        expression: String,
        page: Int,
        filter: Filter
    ): Result<VacancySearchResult> = withContext(Dispatchers.IO) {
        val options = optionMapper.map(expression = expression, page = page, filter = filter)
        vacancyMapper.map(vacancyNetworkClient.execute(options))
    }
}
