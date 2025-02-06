package ru.practicum.android.diploma.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.network.NetworkError
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.ui.search.SearchState.Input
import ru.practicum.android.diploma.ui.search.SearchState.VacanciesList
import ru.practicum.android.diploma.domain.usecase.filters.search.GetSearchFiltersUseCase
import ru.practicum.android.diploma.domain.usecase.filters.search.SetSearchFiltersUseCase
import ru.practicum.android.diploma.domain.usecase.filters.tmp.GetTmpFiltersUseCase
import ru.practicum.android.diploma.domain.usecase.vacancy.GetVacanciesUseCase
import ru.practicum.android.diploma.util.debounce

class SearchViewModel(
    private val getVacanciesUseCase: GetVacanciesUseCase,
    private val getTmpFiltersUseCase: GetTmpFiltersUseCase,
    private val getSearchFiltersUseCase: GetSearchFiltersUseCase,
    private val setSearchFiltersUseCase: SetSearchFiltersUseCase
) : ViewModel() {

    private var lastExpression = ""
    private var currentPage = 1
    private var maxPage = 0

    val isNotLastPage: Boolean
        get() = currentPage < maxPage

    private var isNextPageLoading = false
    private var lastFilter: Filter? = null

    private val _state: MutableStateFlow<SearchState> = MutableStateFlow(
        SearchState(Input.Empty, VacanciesList.Start)
    )
    val state: StateFlow<SearchState>
        get() = _state

    private val searchDebounceAction = debounce<String>(
        delayMillis = 2_000L,
        coroutineScope = viewModelScope,
        useLastParam = true
    ) { changedText ->
        if (changedText != lastExpression && changedText.isNotBlank()) {
            search(changedText)
        }
        if (!compareTmpAndSearchFilters() && changedText == lastExpression && changedText.isNotBlank()) {
            search(lastExpression)
        }
    }

    private fun getFilter() = getTmpFiltersUseCase.execute()

    fun isFilterApplied() = getFilter() != Filter.empty

    fun clearSearch() {
        lastExpression = ""
        _state.value = SearchState(Input.Empty, VacanciesList.Start)
    }

    private fun search(expression: String) {
        lastExpression = expression
        _state.value = SearchState(
            input = Input.Text(lastExpression),
            vacanciesList = VacanciesList.Empty,
        )
        setSearchFiltersUseCase.execute(getTmpFiltersUseCase.execute())
        _state.value = SearchState(Input.Text(expression), VacanciesList.Loading)
        requestToServer(expression)
    }

    private fun requestToServer(expression: String) = viewModelScope.launch {
        isNextPageLoading = true
        val filter = getSearchFiltersUseCase.execute()
        lastFilter = filter
        val result = getVacanciesUseCase.execute(
            expression = expression,
            page = currentPage,
            filter = filter
        )
        val searchState: SearchState = when (result.exceptionOrNull()) {
            is NetworkError.BadCode, is NetworkError.ServerError ->
                state.value.copy(vacanciesList = VacanciesList.Error)

            is NetworkError.NoData ->
                state.value.copy(vacanciesList = VacanciesList.Empty)

            is NetworkError.NoInternet ->
                state.value.copy(vacanciesList = VacanciesList.NoInternet)

            else -> {
                isNextPageLoading = false
                currentPage += 1

                val data = result.getOrNull()
                maxPage = data?.pages ?: 0

                state.value.copy(
                    vacanciesList = VacanciesList.Data(
                        data?.items.orEmpty(),
                        data?.totalVacancyCount ?: 0
                    )
                )
            }
        }
        _state.value = searchState
    }

    fun searchDebounce(expression: String) {
        if (expression.isBlank()) clearSearch()
        searchDebounceAction(expression)
    }

    fun onLastItemReached() {
        if (!isNextPageLoading) requestToServer(lastExpression)
    }

    private fun compareTmpAndSearchFilters(): Boolean {
        val tmpFilters = lastFilter
        val searchFilters = getSearchFiltersUseCase.execute()
        return tmpFilters == searchFilters
    }
}
