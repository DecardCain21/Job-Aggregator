package ru.practicum.android.diploma.data.dto

open class Response {
    var resultCode = 0
}

class ResponseSuccess : Response()
// нужно ли обрабатывать код ошибки
