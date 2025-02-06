package ru.practicum.android.diploma.ui.industry

import ru.practicum.android.diploma.ui.industry.adapter.IndustryItem

data class IndustryState(
    val input: Input,
    val data: Industries,
    // val selectedIndustry: Industry? = null
) {

    sealed interface Input {
        data object Empty : Input
        data class Text(val value: String) : Input
    }

    sealed interface Industries {
        data object NoInternet : Industries
        data object Empty : Industries
        data object Loading : Industries
        data object Error : Industries
        data class Data(val industries: List<IndustryItem>) : Industries
    }
}
