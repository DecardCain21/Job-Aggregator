package ru.practicum.android.diploma.ui.industry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.network.NetworkError
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.toIndustryItem
import ru.practicum.android.diploma.ui.industry.IndustryState.Industries
import ru.practicum.android.diploma.ui.industry.IndustryState.Industries.Data
import ru.practicum.android.diploma.ui.industry.IndustryState.Industries.Error
import ru.practicum.android.diploma.ui.industry.IndustryState.Industries.Loading
import ru.practicum.android.diploma.ui.industry.IndustryState.Industries.NoInternet
import ru.practicum.android.diploma.ui.industry.IndustryState.Input.Empty
import ru.practicum.android.diploma.domain.usecase.GetIndustriesUseCase
import ru.practicum.android.diploma.domain.usecase.filters.tmp.GetTmpFiltersUseCase
import ru.practicum.android.diploma.domain.usecase.filters.tmp.SetTmpFiltersUseCase
import ru.practicum.android.diploma.util.debounce

class IndustryViewModel(
    private val getIndustriesUseCase: GetIndustriesUseCase,
    private val getTmpFiltersUseCase: GetTmpFiltersUseCase,
    private val setTmpFiltersUseCase: SetTmpFiltersUseCase
) : ViewModel() {

    private var listIndustry: List<Industry> = emptyList()
    private var lastCheckedIndustry: Industry? = null

    private val _state: MutableStateFlow<IndustryState> =
        MutableStateFlow(IndustryState(Empty, Loading))
    val state: StateFlow<IndustryState>
        get() = _state

    val searchDebounce: (String) -> Unit = debounce(
        delayMillis = 2_000L,
        coroutineScope = viewModelScope,
        useLastParam = true
    ) { changedText -> getIndustries(changedText) }

    fun getIndustries(sortExpression: String = "") = viewModelScope.launch(Dispatchers.Main) {
        lastCheckedIndustry = getTmpFiltersUseCase.execute().industry
        val result = getIndustriesUseCase.execute()
        val dataState = when (result.exceptionOrNull()) {
            is NetworkError.BadCode, is NetworkError.ServerError -> Error
            is NetworkError.NoData -> Industries.Empty
            is NetworkError.NoInternet -> NoInternet
            else -> {
                listIndustry = result.getOrDefault(emptyList())
                val sortIndustry = searchFilter(listIndustry, sortExpression, lastCheckedIndustry)
                if (sortIndustry.isEmpty()) Industries.Empty else Data(sortIndustry)
            }
        }
        _state.value = state.value.copy(data = dataState)
    }

    fun setFilters(industry: Industry?) {
        val filters = getTmpFiltersUseCase.execute()
            .copy(industry = industry)
        setTmpFiltersUseCase.execute(filters)
        lastCheckedIndustry = industry
        val test = Data(listIndustry.toIndustryItem(industry))
        _state.value = state.value.copy(data = test)
    }

    private fun searchFilter(regions: List<Industry>, sortExpression: String, selectIndustry: Industry?) =
        regions
            .filter { it.name.lowercase().contains(sortExpression.lowercase()) }
            .toIndustryItem(selectIndustry)

    fun clearSearch() {
        getIndustries()
    }
}
