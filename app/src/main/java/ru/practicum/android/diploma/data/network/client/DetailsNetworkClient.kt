package ru.practicum.android.diploma.data.network.client

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.dto.vacancy.details.VacancyDetailsResponse
import ru.practicum.android.diploma.data.network.HeadHunterApiService
import ru.practicum.android.diploma.data.network.NetworkError
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient

class DetailsNetworkClient(
    private val headHunterApiService: HeadHunterApiService
) : RetrofitNetworkClient() {

    suspend fun execute(vacancyId: String): Result<VacancyDetailsResponse> =
        withContext(Dispatchers.IO) {
            val result = super.doRequest { headHunterApiService.getVacancyDetails(vacancyId) }
            result.fold(
                onSuccess = { vacancy ->
                    if (vacancy.isEmpty()) {
                        Result.failure(NetworkError.NoData("Список Country пуст"))
                    } else {
                        Result.success(vacancy)
                    }
                },
                onFailure = { error ->
                    Result.failure(error)
                }
            )
        }
}
