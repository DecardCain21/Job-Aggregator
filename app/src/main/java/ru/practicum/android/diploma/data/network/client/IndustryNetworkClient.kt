package ru.practicum.android.diploma.data.network.client

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.dto.industry.IndustryItemDto
import ru.practicum.android.diploma.data.network.HeadHunterApiService
import ru.practicum.android.diploma.data.network.NetworkError
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient

class IndustryNetworkClient(
    private val headHunterApiService: HeadHunterApiService
) : RetrofitNetworkClient() {

    suspend fun execute(): Result<List<IndustryItemDto>> =
        withContext(Dispatchers.IO) {
            val result = super.doRequest { headHunterApiService.getIndustries() }
            result.fold(
                onSuccess = { industries ->
                    if (industries.isEmpty()) {
                        Result.failure(NetworkError.NoData("Список отраслей пуст"))
                    } else {
                        Result.success(industries)
                    }
                },
                onFailure = { error ->
                    Result.failure(error)
                }
            )
        }
}
