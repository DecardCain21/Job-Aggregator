package ru.practicum.android.diploma.data.network.client

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.dto.VacanciesSearchResponse
import ru.practicum.android.diploma.data.network.HeadHunterApiService
import ru.practicum.android.diploma.data.network.NetworkError
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient

class VacancyNetworkClient(
    private val headHunterApiService: HeadHunterApiService
) : RetrofitNetworkClient() {

    suspend fun execute(options: Map<String, Any>): Result<VacanciesSearchResponse> =
        withContext(Dispatchers.IO) {
            val result = super.doRequest { headHunterApiService.searchVacancies(options) }

            result.fold(
                onSuccess = { vacancies ->
                    if (vacancies.items.isEmpty()) {
                        Result.failure(NetworkError.NoData(""))
                    } else {
                        Result.success(vacancies)
                    }
                },
                onFailure = { error ->
                    Result.failure(error)
                }
            )
        }
}
