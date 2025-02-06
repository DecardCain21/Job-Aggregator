package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.mapper.VacancyDetailsMapper
import ru.practicum.android.diploma.data.network.client.DetailsNetworkClient
import ru.practicum.android.diploma.domain.api.DetailsRepository
import ru.practicum.android.diploma.domain.models.VacancyDetails

class DetailsRepositoryImpl(
    private val detailsNetworkClient: DetailsNetworkClient,
    private val vacancyDetailsMapper: VacancyDetailsMapper,
) : DetailsRepository {

    override suspend fun getVacancyDetails(id: String): Result<VacancyDetails> {
        return withContext(Dispatchers.IO) {
            vacancyDetailsMapper.map(detailsNetworkClient.execute(id))
        }
    }
}
