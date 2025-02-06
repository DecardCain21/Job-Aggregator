package ru.practicum.android.diploma.data.dto.area

import com.google.gson.annotations.SerializedName

class SubAreaDto(
    val id: String,
    val name: String,
    @SerializedName("parent_id")
    val parentId: String
)
