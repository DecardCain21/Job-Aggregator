package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.mapper.CountryMapper
import ru.practicum.android.diploma.data.network.client.CountryNetworkClient
import ru.practicum.android.diploma.domain.api.CountryRepository
import ru.practicum.android.diploma.domain.models.Country

class CountryRepositoryImpl(
    private val countryNetworkClient: CountryNetworkClient,
    private val countryMapper: CountryMapper,
) : CountryRepository {
    override suspend fun getCountry(): Result<List<Country>> =
        withContext(Dispatchers.IO) {
            countryMapper.map(countryNetworkClient.execute())
        }
}
