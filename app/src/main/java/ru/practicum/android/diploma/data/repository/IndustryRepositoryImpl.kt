package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.mapper.IndustryMapper
import ru.practicum.android.diploma.data.network.client.IndustryNetworkClient
import ru.practicum.android.diploma.domain.api.IndustryRepository
import ru.practicum.android.diploma.domain.models.Industry

class IndustryRepositoryImpl(
    private val industryNetworkClient: IndustryNetworkClient,
    private val industryMapper: IndustryMapper,
) : IndustryRepository {

    override suspend fun getIndustries(): Result<List<Industry>> {
        return withContext(Dispatchers.IO) {
            industryMapper.map(industryNetworkClient.execute())
        }
    }
}
