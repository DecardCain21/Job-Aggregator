package ru.practicum.android.diploma.domain.models

import ru.practicum.android.diploma.ui.industry.adapter.IndustryItem

data class Industry(
    val id: String,
    val name: String
)

fun List<Industry>.toIndustryItem(selectIndustry: Industry?): List<IndustryItem> =
    this.map {
        IndustryItem(
            industry = Industry(it.id, it.name),
            isChecked = selectIndustry == it
        )
    }
