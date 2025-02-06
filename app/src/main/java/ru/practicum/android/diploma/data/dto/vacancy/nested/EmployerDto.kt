package ru.practicum.android.diploma.data.dto.vacancy.nested

import com.google.gson.annotations.SerializedName

class EmployerDto(
    val id: String,
    @SerializedName("logo_urls")
    val logoUrls: LogoUrlsDto?,
    val name: String?,
)
