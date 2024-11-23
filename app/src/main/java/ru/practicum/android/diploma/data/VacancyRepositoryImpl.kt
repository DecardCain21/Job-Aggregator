package ru.practicum.android.diploma.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.dto.VacanciesSearchResponse
import ru.practicum.android.diploma.data.dto.VacancyDetailsRequest
import ru.practicum.android.diploma.data.dto.VacancySearchRequest
import ru.practicum.android.diploma.data.dto.vacancy.details.VacancyDetailsDto
import ru.practicum.android.diploma.data.mapper.OptionMapper
import ru.practicum.android.diploma.data.mapper.VacancyDetailsMapper
import ru.practicum.android.diploma.data.mapper.VacancyMapper
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient.Companion.FAILED_INTERNET_CONNECTION_CODE
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient.Companion.NOT_FOUND_CODE
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient.Companion.SUCCESS_CODE
import ru.practicum.android.diploma.domain.api.VacancyRepository
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.models.VacancySearchResult
import ru.practicum.android.diploma.util.Resource

class VacancyRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacancyMapper: VacancyMapper,
    private val vacancyDetailsMapper: VacancyDetailsMapper,
    private val optionMapper: OptionMapper
) : VacancyRepository {

    override suspend fun searchVacancies(expression: String, page: Int): Resource<VacancySearchResult> {
        return withContext(Dispatchers.IO) {
            // заменить filterRepository
            val filterRepository = mutableListOf("40", "", "", true)
            val response = networkClient.doRequest(
                VacancySearchRequest(
                    optionMapper.map(
                        expression = expression,
                        page = page.toString(),
                        area = filterRepository[0].toString(),
                        salary = filterRepository[1].toString(),
                        industry = filterRepository[2].toString(),
                        onlyWithSalary = filterRepository[3].toString(),
                    )
                )
            )
            when (response.resultCode) {
                FAILED_INTERNET_CONNECTION_CODE -> Resource.Error("-1")

                SUCCESS_CODE -> {
                    val data = vacancyMapper.map(response as VacanciesSearchResponse)
                    Resource.Success(data)
                }

                else -> Resource.Error("Server Error")
            }
        }
    }

    override suspend fun getVacancyDetails(id: String): Resource<VacancyDetails> {
        return withContext(Dispatchers.IO) {
            val response = networkClient.doRequest(VacancyDetailsRequest(id))
            when (response.resultCode) {
                FAILED_INTERNET_CONNECTION_CODE -> Resource.Error("-1")
                NOT_FOUND_CODE -> Resource.Error("404")
                SUCCESS_CODE -> {
                    val data = vacancyDetailsMapper.map(response as VacancyDetailsDto)
                    Resource.Success(data)
                }

                else -> Resource.Error("Server Error")
            }
        }
    }
}
