package ru.practicum.android.diploma.ui.region

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.network.NetworkError
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Location
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.ui.region.RegionState.Data
import ru.practicum.android.diploma.ui.region.RegionState.Input
import ru.practicum.android.diploma.domain.usecase.GetCountriesUseCase
import ru.practicum.android.diploma.domain.usecase.filters.location.GetLocationUseCase
import ru.practicum.android.diploma.domain.usecase.filters.location.SetLocationUseCase
import ru.practicum.android.diploma.util.debounce

class RegionViewModel(
    private val setLocationUseCase: SetLocationUseCase,
    private val getLocationUseCase: GetLocationUseCase,
    private val getCountriesUseCase: GetCountriesUseCase
) : ViewModel() {

    private var lastExpression = ""
    private var countries: List<Country> = emptyList()

    private val _state: MutableStateFlow<RegionState> =
        MutableStateFlow(RegionState(Input.Empty, Data.Loading))
    val state: StateFlow<RegionState>
        get() = _state

    fun getRegions(sortExpression: String = "") = viewModelScope.launch(Dispatchers.Main) {
        val result = getCountriesUseCase.execute()
        countries = result.getOrDefault(emptyList())
        val dataState: Data = when (result.exceptionOrNull()) {
            is NetworkError.BadCode, is NetworkError.ServerError -> Data.Error
            is NetworkError.NoData -> Data.Empty
            is NetworkError.NoInternet -> Data.NoInternet
            else -> {
                val sortedRegions = sortRegionsIfNeeded(parseRegions(result.getOrDefault(emptyList())), sortExpression)
                if (sortedRegions.isEmpty()) Data.Empty else Data.Data(sortedRegions)
            }
        }
        _state.value = state.value.copy(data = dataState)
    }

    private val searchDebounceAction = debounce<String>(
        delayMillis = 2_000L,
        coroutineScope = viewModelScope,
        useLastParam = true
    ) { changedText ->
        if (changedText != lastExpression) {
            _state.value = state.value.copy(data = Data.Loading, input = Input.Text(changedText))
            getRegions(changedText)
        }
    }

    private fun sortRegionsIfNeeded(regions: List<Region>, sortExpression: String): List<Region> {
        return if (sortExpression.isNotBlank()) {
            regions.filter { it.name.contains(sortExpression, ignoreCase = true) }
                .sortedBy { it.name }
        } else {
            regions
        }
    }

    fun clearRegion() {
        _state.value = state.value.copy(data = Data.Loading, input = Input.Empty)
        getRegions()
    }

    fun sortDebounce(expression: String) {
        if (expression.isBlank()) return
        searchDebounceAction(expression.trim())
    }

    fun setRegion(region: Region) {
        val country = findCountryByRegion(region)
        setLocationUseCase.execute(Location(country, region))
    }

    private fun parseRegions(countries: List<Country>): List<Region> {
        val filter = getLocationUseCase.execute()
        val regions = if (filter.country == null) {
            countries.flatMap { it.regions }
        } else {
            filter.country.regions
        }
        return regions
    }

    private fun findCountryByRegion(neededRegion: Region): Country? = countries
        .firstOrNull { country -> country.regions.contains(neededRegion) }
}
