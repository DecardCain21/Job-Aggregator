package ru.practicum.android.diploma.ui.location

import ru.practicum.android.diploma.domain.models.Location

sealed interface LocationState {

    data class Data(
        val location: Location
    ) : LocationState

    data object Empty : LocationState
}
