package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.Response

class ApiResponse<T> : Response() {
    var data: T? = null
    var message: String? = null
}
