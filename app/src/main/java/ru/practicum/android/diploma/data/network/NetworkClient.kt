package ru.practicum.android.diploma.data.network

interface NetworkClient {
    suspend fun <T> doRequest(request: suspend () -> T): Result<T>
}
