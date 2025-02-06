package ru.practicum.android.diploma.ui.country

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.network.NetworkError
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Location
import ru.practicum.android.diploma.ui.country.CountryState.Loading
import ru.practicum.android.diploma.domain.usecase.GetCountriesUseCase
import ru.practicum.android.diploma.domain.usecase.filters.location.SetLocationUseCase

class CountryViewModel(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val setLocationUseCase: SetLocationUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<CountryState> = MutableStateFlow(Loading)
    val state: StateFlow<CountryState>
        get() = _state

    fun getCountries() = viewModelScope.launch {

        val result = getCountriesUseCase.execute()
        _state.value = when (result.exceptionOrNull()) {
            is NetworkError.BadCode, is NetworkError.ServerError -> CountryState.Error
            is NetworkError.NoData -> CountryState.Empty
            is NetworkError.NoInternet -> CountryState.NoInternet
            else -> result.getOrNull()?.let { CountryState.Data(it) } ?: CountryState.Error
        }
    }

    fun setCountry(country: Country) {
        val location = Location(country = country, region = null)
        setLocationUseCase.execute(location)
    }
}
