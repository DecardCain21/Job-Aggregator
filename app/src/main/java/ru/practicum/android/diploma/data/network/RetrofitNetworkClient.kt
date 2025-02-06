package ru.practicum.android.diploma.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.util.getConnected

abstract class RetrofitNetworkClient : NetworkClient {

    override suspend fun <T> doRequest(request: suspend () -> T): Result<T> {
        if (!getConnected()) {
            return Result.failure(NetworkError.NoInternet())
        }
        return withContext(Dispatchers.IO) {
            try {
                Result.success(request())
            } catch (e: HttpException) {
                Result.failure(NetworkError.ServerError("", e.toString()))
            }
        }
    }
}
